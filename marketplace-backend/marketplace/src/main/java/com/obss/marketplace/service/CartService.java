package com.obss.marketplace.service;

import com.obss.marketplace.dto.CartDTO;
import com.obss.marketplace.dto.CartItemDTO;
import com.obss.marketplace.model.Cart;
import com.obss.marketplace.model.CartItem;
import com.obss.marketplace.model.Product;
import com.obss.marketplace.model.User;
import com.obss.marketplace.repository.CartRepository;
import com.obss.marketplace.repository.CartItemRepository;
import com.obss.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found for user"));
    }

    public Cart addItemToCart(User user, Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> new CartItem(cart, product, 0));
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);

        return cart;
    }

    public Cart removeItemFromCart(User user, Long productId) {
        Cart cart = getCartByUser(user);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);

        return cart;
    }

    public void clearCart(User user) {
        Cart cart = getCartByUser(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}

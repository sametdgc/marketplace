package com.obss.marketplace.controller;

import com.obss.marketplace.dto.CartDTO;
import com.obss.marketplace.dto.CartItemDTO;
import com.obss.marketplace.dto.UserDTO;
import com.obss.marketplace.model.Cart;
import com.obss.marketplace.model.User;
import com.obss.marketplace.response.ApiResponse;
import com.obss.marketplace.service.CartService;
import com.obss.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByEmail(userDetails.getUsername());
    }

    private CartDTO convertToDto(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());

        // Manually set properties for UserDTO
        UserDTO userDTO = new UserDTO();
        User user = cart.getUser();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword()); // It's typically not a good idea to expose passwords

        cartDTO.setUser(userDTO);
        cartDTO.setItems(cart.getItems().stream()
                .map(item -> {
                    CartItemDTO itemDTO = new CartItemDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setProductId(item.getProduct().getId());
                    itemDTO.setQuantity(item.getQuantity());
                    return itemDTO;
                })
                .collect(Collectors.toList()));
        return cartDTO;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartDTO>> getCart() {
        User user = getCurrentUser();
        Cart cart = cartService.getCartByUser(user);
        CartDTO cartDTO = convertToDto(cart);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart retrieved successfully", cartDTO));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartDTO>> addItemToCart(@RequestParam Long productId,
                                                              @RequestParam int quantity) {
        User user = getCurrentUser();
        Cart cart = cartService.addItemToCart(user, productId, quantity);
        CartDTO cartDTO = convertToDto(cart);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item added to cart", cartDTO));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<CartDTO>> removeItemFromCart(@RequestParam Long productId) {
        User user = getCurrentUser();
        Cart cart = cartService.removeItemFromCart(user, productId);
        CartDTO cartDTO = convertToDto(cart);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item removed from cart", cartDTO));
    }

    @PostMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        User user = getCurrentUser();
        cartService.clearCart(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart cleared", null));
    }
}

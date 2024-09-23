package com.obss.marketplace.repository;

import com.obss.marketplace.model.Cart;
import com.obss.marketplace.model.CartItem;
import com.obss.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}

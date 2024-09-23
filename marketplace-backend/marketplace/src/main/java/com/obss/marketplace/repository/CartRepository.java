package com.obss.marketplace.repository;

import com.obss.marketplace.model.Cart;
import com.obss.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}

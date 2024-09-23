package com.obss.marketplace.repository;

import com.obss.marketplace.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Page<Seller> findByUserFirstNameContainingOrUserLastNameContainingOrUserEmailContaining(String firstName, String lastName, String email, Pageable pageable);

    Optional<Seller> findByUserEmail(String email);
}

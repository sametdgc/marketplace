package com.obss.marketplace.repository;

import com.obss.marketplace.model.Category;
import com.obss.marketplace.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);
    Optional<Product> findByIdAndSellerId(Long productId, Long sellerId);
    Page<Product> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);

    Page<Product> findAllBySellerId(Long sellerId, PageRequest pageRequest);
}

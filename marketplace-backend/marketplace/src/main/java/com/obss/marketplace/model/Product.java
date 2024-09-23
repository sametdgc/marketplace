package com.obss.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 50)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @Size(min = 10, max = 500)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @DecimalMin("0.0")
    private BigDecimal price;

    @Column(nullable = false)
    @Min(0)
    private Integer quantity;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @ManyToMany(mappedBy = "favoriteProducts")
    private Set<User> favoritedByUsers = new HashSet<>();
}


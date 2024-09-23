package com.obss.marketplace.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
    private String categoryName;  // Category name for the product
    private String sellerFirstName;  // Seller's first name
    private String sellerLastName;  // Seller's last name
}

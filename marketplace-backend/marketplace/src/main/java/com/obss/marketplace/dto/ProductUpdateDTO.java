package com.obss.marketplace.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
    private String categoryName;
}

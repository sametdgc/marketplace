package com.obss.marketplace.dto;

import lombok.Data;

@Data
public class SellerDTO {
    private Long sellerId;
    private String firstName;
    private String lastName;
    private String email;
}

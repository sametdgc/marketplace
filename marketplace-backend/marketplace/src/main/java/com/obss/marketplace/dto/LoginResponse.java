package com.obss.marketplace.dto;

import com.obss.marketplace.model.Role;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

    private String role;

}
package com.obss.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterUserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
package com.obss.marketplace.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String firstName;
    private String password;
    private String lastName;
    private String email;
}

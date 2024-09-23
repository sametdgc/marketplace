package com.obss.marketplace.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String password;
    private String lastName;
    private String email;
}

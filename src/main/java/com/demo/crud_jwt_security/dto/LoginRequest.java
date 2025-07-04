package com.demo.crud_jwt_security.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;

}

package com.myblog15.blogapp15.payload;

import lombok.Data;

@Data
public class LoginDto { //I want json content or front end username and password into LoginDto
    private String usernameOrEmail;
    private String password;
}

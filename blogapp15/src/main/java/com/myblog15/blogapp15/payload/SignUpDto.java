package com.myblog15.blogapp15.payload;

import lombok.Data;

@Data
public class SignUpDto {
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;

}

package com.myblog15.blogapp15.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePassword { //This is a passwordEncoder you cannot set text password directly into data base first we have to encode it into BASE64. By using encrption technique it will convert plain text password to hash value or it will generate hash value, hash value then encoded as a Base64 String
    public static void main(String[] args) {
        PasswordEncoder encodePassword= new BCryptPasswordEncoder();
        System.out.println(encodePassword.encode("admin"));
    }// This class dose not require just for checking Encode password how it will work.
}

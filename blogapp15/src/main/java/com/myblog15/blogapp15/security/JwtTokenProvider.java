package com.myblog15.blogapp15.security;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    //app.jwt-secret=JWTSecretKey From signature part key value pair from application.properties file
    private String jwtSecret;  //I will take this value JWTSecretKey and initialize that into private String jwtSecret; I need to read the content from the properties files give it to this variable.
    @Value("${app.jwt-expiration-milliseconds}")
//app.jwt-expiration-milliseconds = 604800000 //This will be Expiry date 7days.
    private int jwtExpirationInMs;//Here we initialize the value 604800000 to  private int jwtExpirationInMs;

    // generate token
    public String generateToken(Authentication authentication) {// Now generate token now this code generate Token and return the Token
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    // get username from the token
    public String getUsernameFromJWT(String token) {// This will get the Username from the token
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {// Whatever token will be there it will validate that
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            //throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }
        return false;// If everything is true it will return True or else it will return false instead of exception.
    }

}

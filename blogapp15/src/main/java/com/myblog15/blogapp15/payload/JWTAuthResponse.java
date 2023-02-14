package com.myblog15.blogapp15.payload;

public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";// private String tokenType = "Bearer"; Here Bearer Token Type, This will initialize the Object and that Object is being return back to AuthController

    public JWTAuthResponse(String accessToken) { // Here Payload class we give this Token or initialize it to accessToken
        this.accessToken = accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}

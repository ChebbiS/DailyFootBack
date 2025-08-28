package com.dailyfoot.dto;

public class LoginResponse {
    private String name;
    private String token;
    public LoginResponse(String name , String token) {
        this.name = name;
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}



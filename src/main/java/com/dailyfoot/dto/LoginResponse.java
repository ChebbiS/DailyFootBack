package com.dailyfoot.dto;

public class LoginResponse {
    private String name;

    public LoginResponse(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}



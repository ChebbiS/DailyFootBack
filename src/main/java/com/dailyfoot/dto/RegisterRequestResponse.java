package com.dailyfoot.dto;

public class RegisterRequestResponse {
    private String message;
    public RegisterRequestResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


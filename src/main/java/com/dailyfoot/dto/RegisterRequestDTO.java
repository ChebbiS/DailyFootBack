package com.dailyfoot.dto;

public class RegisterRequestDTO {
    private String message;

    public RegisterRequestDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


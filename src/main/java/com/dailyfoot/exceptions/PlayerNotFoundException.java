package com.dailyfoot.exceptions;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}

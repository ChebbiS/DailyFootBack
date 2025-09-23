package com.dailyfoot.exceptions;

public class CannotUpdatePlayerException extends RuntimeException {
    public CannotUpdatePlayerException(String message) {
        super(message);
    }
}
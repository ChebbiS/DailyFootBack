package com.dailyfoot.exceptions;

public class CannotDeleteStrangerPlayerException extends RuntimeException {
    public CannotDeleteStrangerPlayerException(String message) {
        super(message);
    }
}

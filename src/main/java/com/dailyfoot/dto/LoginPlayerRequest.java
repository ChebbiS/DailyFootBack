package com.dailyfoot.dto;

import jakarta.validation.constraints.NotNull;

public class LoginPlayerRequest {
    @NotNull(message = "Le code d'accès est obligatoire")
    private int codeAccess;

    public LoginPlayerRequest() {
    }

    public LoginPlayerRequest(int codeAccess) {
        this.codeAccess = codeAccess;
    }

    public int getCodeAccess() {
        return codeAccess;
    }

    public void setCodeAccess(int codeAccess) {
        this.codeAccess = codeAccess;
    }
}

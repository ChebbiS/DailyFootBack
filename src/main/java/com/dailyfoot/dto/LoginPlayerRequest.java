package com.dailyfoot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginPlayerRequest {
    @NotBlank(message = "Le code d'accès est obligatoire")
    @NotNull
    private int codeAccess;

    public LoginPlayerRequest() {}
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

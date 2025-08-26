package com.dailyfoot.dto;

public class LoginPlayerRequest {
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

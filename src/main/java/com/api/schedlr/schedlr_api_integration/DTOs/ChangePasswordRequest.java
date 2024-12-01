package com.api.schedlr.schedlr_api_integration.DTOs;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String password;
    private int userId;

    // Getters and Setters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

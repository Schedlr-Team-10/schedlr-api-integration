package com.api.schedlr.schedlr_api_integration.DTOs;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String password;
    private int userId;

    // Getters and Setters
}

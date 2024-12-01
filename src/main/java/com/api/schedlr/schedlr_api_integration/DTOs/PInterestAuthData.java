package com.api.schedlr.schedlr_api_integration.DTOs;

import lombok.Data;

@Data
public class PInterestAuthData {
    String userId;
    String code;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

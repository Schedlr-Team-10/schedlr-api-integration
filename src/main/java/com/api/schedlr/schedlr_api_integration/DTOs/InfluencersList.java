package com.api.schedlr.schedlr_api_integration.DTOs;

import lombok.Data;

@Data
public class InfluencersList {
    String userId;
    String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
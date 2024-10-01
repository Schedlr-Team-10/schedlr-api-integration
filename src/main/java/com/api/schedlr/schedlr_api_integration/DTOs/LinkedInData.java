package com.api.schedlr.schedlr_api_integration.DTOs;

import lombok.Data;

@Data
public class LinkedInData {
    private String accessToken;
    private String personId;

    public LinkedInData(String accessToken, String personId) {
        this.accessToken = accessToken;
        this.personId= personId;
    }
}

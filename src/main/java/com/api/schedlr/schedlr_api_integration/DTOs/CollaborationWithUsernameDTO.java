package com.api.schedlr.schedlr_api_integration.DTOs;

import com.api.schedlr.schedlr_api_integration.entity.Collaboration;
import lombok.Data;

@Data
public class CollaborationWithUsernameDTO {
    private Collaboration collaboration;
    private String username;
    public CollaborationWithUsernameDTO(Collaboration collaboration, String username) {
        this.collaboration = collaboration;
        this.username = username;
    }
}
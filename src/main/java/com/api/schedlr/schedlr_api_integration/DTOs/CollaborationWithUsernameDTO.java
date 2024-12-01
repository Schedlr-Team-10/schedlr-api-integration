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

    public Collaboration getCollaboration() {
        return collaboration;
    }

    public void setCollaboration(Collaboration collaboration) {
        this.collaboration = collaboration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
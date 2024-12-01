package com.api.schedlr.schedlr_api_integration.DTOs;

import com.api.schedlr.schedlr_api_integration.entity.Collaboration;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class CollaborationDto {
    private int id;

    private int userId;

    private int influencerId;

    private String message;

    private Collaboration.Status status = Collaboration.Status.PENDING;

    private String collaborationToken;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        PAYMENT_PENDING,
        COMPLETED
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInfluencerId() {
        return influencerId;
    }

    public void setInfluencerId(int influencerId) {
        this.influencerId = influencerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collaboration.Status getStatus() {
        return status;
    }

    public void setStatus(Collaboration.Status status) {
        this.status = status;
    }

    public String getCollaborationToken() {
        return collaborationToken;
    }

    public void setCollaborationToken(String collaborationToken) {
        this.collaborationToken = collaborationToken;
    }
}

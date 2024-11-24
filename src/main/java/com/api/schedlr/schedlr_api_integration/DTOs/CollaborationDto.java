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
}

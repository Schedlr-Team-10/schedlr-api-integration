package com.api.schedlr.schedlr_api_integration.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@Entity
@Table(name = "collaboration", schema = "schedlr")
@Check(constraints = "user_id <> influencer_id")
public class Collaboration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "influencer_id", nullable = false)
    private int influencerId;

    @Lob
    @Column(name = "message", columnDefinition = "LONGTEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "collaboration_token", nullable = true, unique = true)
    private String collaborationToken;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        PAYMENT_PENDING,
        COMPLETED
    }
}

package com.api.schedlr.schedlr_api_integration.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedulePostUpload")
public class SchedulePostUpload {

    @Id
    private int postId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String image;
    private String description;

    private LocalDateTime scheduleTime;

    private boolean fb = false;
    private boolean insta = false;
    private boolean twitter = false;
    private boolean linkedIn = false;

    // Constructors, getters, and setters
}

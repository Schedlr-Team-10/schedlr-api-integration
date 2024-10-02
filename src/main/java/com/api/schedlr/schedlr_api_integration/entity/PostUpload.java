package com.api.schedlr.schedlr_api_integration.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "postUpload")
public class PostUpload {

    @Id
    private int postId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String image;
    private String description;

    private String fbPostId;
    private String instaPostId;
    private String twitterPostId;
    private String linkedInPostId;

    private LocalDateTime postUploadDate;

    // Constructors, getters, and setters

}
package com.api.schedlr.schedlr_api_integration.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    private int profileId;

    private int userId;

    private String faceBookAccessToken;
    private LocalDateTime faceBookAccessTokenExpireDate;
    @Lob
    private String linkedInAccessToken;
    private LocalDateTime linkedInAccessTokenExpireDate;

    private String linkedInPersonId;

    private String instaAccessToken;
    private LocalDateTime instaAccessTokenExpireDate;

    private String twitterAccessToken;
    private LocalDateTime twitterAccessTokenExpireDate;

    @Lob
    private String pinterestAccessToken;
    private LocalDateTime pinterestAccessTokenExpireDate;

    // Constructors, getters, and setters
}

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


    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFaceBookAccessToken() {
        return faceBookAccessToken;
    }

    public void setFaceBookAccessToken(String faceBookAccessToken) {
        this.faceBookAccessToken = faceBookAccessToken;
    }

    public LocalDateTime getFaceBookAccessTokenExpireDate() {
        return faceBookAccessTokenExpireDate;
    }

    public void setFaceBookAccessTokenExpireDate(LocalDateTime faceBookAccessTokenExpireDate) {
        this.faceBookAccessTokenExpireDate = faceBookAccessTokenExpireDate;
    }

    public String getLinkedInAccessToken() {
        return linkedInAccessToken;
    }

    public void setLinkedInAccessToken(String linkedInAccessToken) {
        this.linkedInAccessToken = linkedInAccessToken;
    }

    public LocalDateTime getLinkedInAccessTokenExpireDate() {
        return linkedInAccessTokenExpireDate;
    }

    public void setLinkedInAccessTokenExpireDate(LocalDateTime linkedInAccessTokenExpireDate) {
        this.linkedInAccessTokenExpireDate = linkedInAccessTokenExpireDate;
    }

    public String getLinkedInPersonId() {
        return linkedInPersonId;
    }

    public void setLinkedInPersonId(String linkedInPersonId) {
        this.linkedInPersonId = linkedInPersonId;
    }

    public String getInstaAccessToken() {
        return instaAccessToken;
    }

    public void setInstaAccessToken(String instaAccessToken) {
        this.instaAccessToken = instaAccessToken;
    }

    public LocalDateTime getInstaAccessTokenExpireDate() {
        return instaAccessTokenExpireDate;
    }

    public void setInstaAccessTokenExpireDate(LocalDateTime instaAccessTokenExpireDate) {
        this.instaAccessTokenExpireDate = instaAccessTokenExpireDate;
    }

    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    public LocalDateTime getTwitterAccessTokenExpireDate() {
        return twitterAccessTokenExpireDate;
    }

    public void setTwitterAccessTokenExpireDate(LocalDateTime twitterAccessTokenExpireDate) {
        this.twitterAccessTokenExpireDate = twitterAccessTokenExpireDate;
    }

    public String getPinterestAccessToken() {
        return pinterestAccessToken;
    }

    public void setPinterestAccessToken(String pinterestAccessToken) {
        this.pinterestAccessToken = pinterestAccessToken;
    }

    public LocalDateTime getPinterestAccessTokenExpireDate() {
        return pinterestAccessTokenExpireDate;
    }

    public void setPinterestAccessTokenExpireDate(LocalDateTime pinterestAccessTokenExpireDate) {
        this.pinterestAccessTokenExpireDate = pinterestAccessTokenExpireDate;
    }
}

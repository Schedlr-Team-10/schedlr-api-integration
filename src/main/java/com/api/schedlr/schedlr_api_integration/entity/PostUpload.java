package com.api.schedlr.schedlr_api_integration.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "post_upload")
public class PostUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "fb_post_id")
    private String fbPostId;

    @Column(name = "pinterest_post_id")
    private String pinterestPostId;

    @Column(name = "twitter_post_id")
    private String twitterPostId;

    @Column(name = "linkedin_post_id")
    private String linkedinPostId;

    @Column(name = "post_upload_date")
    private LocalDateTime postUploadDate;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFbPostId() {
        return fbPostId;
    }

    public void setFbPostId(String fbPostId) {
        this.fbPostId = fbPostId;
    }

    public String getPinterestPostId() {
        return pinterestPostId;
    }

    public void setPinterestPostId(String pinterestPostId) {
        this.pinterestPostId = pinterestPostId;
    }

    public String getTwitterPostId() {
        return twitterPostId;
    }

    public void setTwitterPostId(String twitterPostId) {
        this.twitterPostId = twitterPostId;
    }

    public String getLinkedinPostId() {
        return linkedinPostId;
    }

    public void setLinkedinPostId(String linkedinPostId) {
        this.linkedinPostId = linkedinPostId;
    }

    public LocalDateTime getPostUploadDate() {
        return postUploadDate;
    }

    public void setPostUploadDate(LocalDateTime postUploadDate) {
        this.postUploadDate = postUploadDate;
    }
}
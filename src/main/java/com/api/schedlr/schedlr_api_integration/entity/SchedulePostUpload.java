package com.api.schedlr.schedlr_api_integration.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "schedule_post_upload")
public class SchedulePostUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "schedule_time", nullable = false)
    private LocalDateTime scheduleTime;

    @Column(nullable = false)
    private boolean fb = false;

    @Column(nullable = false)
    private boolean pinterest = false;

    @Column(nullable = false)
    private boolean twitter = false;

    @Column(nullable = false)
    private boolean linkedin = false;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDateTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalDateTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public boolean isFb() {
        return fb;
    }

    public void setFb(boolean fb) {
        this.fb = fb;
    }

    public boolean isPinterest() {
        return pinterest;
    }

    public void setPinterest(boolean pinterest) {
        this.pinterest = pinterest;
    }

    public boolean isTwitter() {
        return twitter;
    }

    public void setTwitter(boolean twitter) {
        this.twitter = twitter;
    }

    public boolean isLinkedin() {
        return linkedin;
    }

    public void setLinkedin(boolean linkedin) {
        this.linkedin = linkedin;
    }
}
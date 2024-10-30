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

}
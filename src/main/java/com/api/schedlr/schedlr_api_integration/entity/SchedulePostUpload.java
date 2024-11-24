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

}
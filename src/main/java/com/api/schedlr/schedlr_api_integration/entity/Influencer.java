package com.api.schedlr.schedlr_api_integration.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "influencers", schema = "schedlr")
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int influencerId;

    @Column(nullable = false)
    private int userid;

    @Lob
    @Column(nullable = false)
    private byte[] profilePic;

    @Lob
    @Column(nullable = false)
    private String bio;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int linkedinFollowers;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int pinterestFollowers;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int twitterFollowers;

    private String linkedinProfile;
    private String pinterestProfile;
    private String twitterProfile;

    @Column(nullable = false)
    private int pricePerPhoto;

    @Column(nullable = false)
    private int pricePerVideo;

    @Column(nullable = false)
    private int pricePerTweet;

    @Convert(converter = com.api.schedlr.schedlr_api_integration.util.JsonConverter.class)
    private List<String> tags;

}

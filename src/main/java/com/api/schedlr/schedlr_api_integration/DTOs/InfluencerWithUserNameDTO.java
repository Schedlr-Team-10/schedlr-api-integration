package com.api.schedlr.schedlr_api_integration.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class InfluencerWithUserNameDTO {

    private int influencerId;
    private String name;
    private int userid;
    private byte[] profilePic;
    private String bio;
    private int linkedinFollowers;
    private int pinterestFollowers;
    private int twitterFollowers;
    private String linkedinProfile;
    private String pinterestProfile;
    private String twitterProfile;
    private int pricePerPhoto;
    private int pricePerVideo;
    private int pricePerTweet;
    private List<String> tags;

    // Constructor, Getters, and Setters

    public InfluencerWithUserNameDTO(int  influencerId, String linkedinProfile,
                                     String pinterestProfile, String twitterProfile, int pricePerPhoto,
                                     List<String> tags, String username) {
        this.influencerId = influencerId;
        this.linkedinProfile = linkedinProfile;
        this.pinterestProfile = pinterestProfile;
        this.twitterProfile = twitterProfile;
        this.pricePerPhoto = pricePerPhoto;
        this.tags = tags;
        this.name = username;
    }
}
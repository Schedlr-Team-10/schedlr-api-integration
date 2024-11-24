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

    public InfluencerWithUserNameDTO(int  influencerId, byte[] profilePic, String bio, int linkedinFollowers,
                                     int pinterestFollowers, int twitterFollowers, String linkedinProfile,
                                     String pinterestProfile, String twitterProfile, int pricePerPhoto,
                                     int pricePerVideo, int pricePerTweet, List<String> tags, String username) {
        this.influencerId = influencerId;
        this.profilePic = profilePic;
        this.bio = bio;
        this.linkedinFollowers = linkedinFollowers;
        this.pinterestFollowers = pinterestFollowers;
        this.twitterFollowers = twitterFollowers;
        this.linkedinProfile = linkedinProfile;
        this.pinterestProfile = pinterestProfile;
        this.twitterProfile = twitterProfile;
        this.pricePerPhoto = pricePerPhoto;
        this.pricePerVideo = pricePerVideo;
        this.pricePerTweet = pricePerTweet;
        this.tags = tags;
        this.name = username;
    }
}
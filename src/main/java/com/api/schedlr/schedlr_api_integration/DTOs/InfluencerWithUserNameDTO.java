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

    public int getInfluencerId() {
        return influencerId;
    }

    public void setInfluencerId(int influencerId) {
        this.influencerId = influencerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getLinkedinFollowers() {
        return linkedinFollowers;
    }

    public void setLinkedinFollowers(int linkedinFollowers) {
        this.linkedinFollowers = linkedinFollowers;
    }

    public int getPinterestFollowers() {
        return pinterestFollowers;
    }

    public void setPinterestFollowers(int pinterestFollowers) {
        this.pinterestFollowers = pinterestFollowers;
    }

    public int getTwitterFollowers() {
        return twitterFollowers;
    }

    public void setTwitterFollowers(int twitterFollowers) {
        this.twitterFollowers = twitterFollowers;
    }

    public String getLinkedinProfile() {
        return linkedinProfile;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public String getPinterestProfile() {
        return pinterestProfile;
    }

    public void setPinterestProfile(String pinterestProfile) {
        this.pinterestProfile = pinterestProfile;
    }

    public String getTwitterProfile() {
        return twitterProfile;
    }

    public void setTwitterProfile(String twitterProfile) {
        this.twitterProfile = twitterProfile;
    }

    public int getPricePerPhoto() {
        return pricePerPhoto;
    }

    public void setPricePerPhoto(int pricePerPhoto) {
        this.pricePerPhoto = pricePerPhoto;
    }

    public int getPricePerVideo() {
        return pricePerVideo;
    }

    public void setPricePerVideo(int pricePerVideo) {
        this.pricePerVideo = pricePerVideo;
    }

    public int getPricePerTweet() {
        return pricePerTweet;
    }

    public void setPricePerTweet(int pricePerTweet) {
        this.pricePerTweet = pricePerTweet;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
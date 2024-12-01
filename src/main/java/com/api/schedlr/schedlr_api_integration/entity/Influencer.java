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
    private int id;

    @Column(nullable = false)
    private int userid;

    private String linkedinProfile;
    private String pinterestProfile;
    private String twitterProfile;

    @Column(nullable = false)
    private int pricePerPhoto;

    @Convert(converter = com.api.schedlr.schedlr_api_integration.util.JsonConverter.class)
    private List<String> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

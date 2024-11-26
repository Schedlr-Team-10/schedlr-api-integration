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

}

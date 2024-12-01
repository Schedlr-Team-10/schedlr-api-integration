package com.api.schedlr.schedlr_api_integration.DTOs;

import lombok.Data;

@Data
public class InfluencerRequest {
    private String influencerName;
    private int influencerId;

    public String getInfluencerName() {
        return influencerName;
    }

    public void setInfluencerName(String influencerName) {
        this.influencerName = influencerName;
    }

    public int getInfluencerId() {
        return influencerId;
    }

    public void setInfluencerId(int influencerId) {
        this.influencerId = influencerId;
    }
}

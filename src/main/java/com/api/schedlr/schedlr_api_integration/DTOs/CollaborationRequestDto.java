package com.api.schedlr.schedlr_api_integration.DTOs;

import lombok.Data;

@Data
public class CollaborationRequestDto {
    InfluencerWithUserNameDTO influencerWithUserNameDTO;
    CollaborationDto collaborationDto;

    public InfluencerWithUserNameDTO getInfluencerWithUserNameDTO() {
        return influencerWithUserNameDTO;
    }

    public void setInfluencerWithUserNameDTO(InfluencerWithUserNameDTO influencerWithUserNameDTO) {
        this.influencerWithUserNameDTO = influencerWithUserNameDTO;
    }

    public CollaborationDto getCollaborationDto() {
        return collaborationDto;
    }

    public void setCollaborationDto(CollaborationDto collaborationDto) {
        this.collaborationDto = collaborationDto;
    }
}

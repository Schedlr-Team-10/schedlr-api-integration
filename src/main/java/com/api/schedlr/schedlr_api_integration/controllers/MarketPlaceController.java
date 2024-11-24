package com.api.schedlr.schedlr_api_integration.controllers;


import com.api.schedlr.schedlr_api_integration.DTOs.CollaborationRequestDto;
import com.api.schedlr.schedlr_api_integration.DTOs.InfluencerRequest;
import com.api.schedlr.schedlr_api_integration.DTOs.InfluencerWithUserNameDTO;
import com.api.schedlr.schedlr_api_integration.Service.InfluencerService;
import com.api.schedlr.schedlr_api_integration.entity.Influencer;
import com.api.schedlr.schedlr_api_integration.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/influencers")
public class MarketPlaceController {

    @Autowired
    InfluencerService influencerService;

//    @GetMapping("/{userid}")
//    public ResponseEntity<Influencer> getInfluencersByUserId(@PathVariable int userid) {
//        Influencer influencers = influencerService.getInfluencersByUserId(userid);
//        return ResponseEntity.ok(influencers);
//    }

    @PostMapping
    public ResponseEntity<Influencer> createInfluencer(@RequestBody Influencer influencer) {
        Influencer savedInfluencer = influencerService.saveInfluencer(influencer);
        return ResponseEntity.ok(savedInfluencer);
    }

    @GetMapping("/findallInfl")
    public List<User> findAllInfluencersData() {
        return influencerService.getInfluencersList();
    }

    @PostMapping("/{userId}")
    public CollaborationRequestDto findByUserId(
            @PathVariable int userId,
            @RequestBody InfluencerRequest request) {
        log.info("UserId: "+ userId);
        log.info("Request: "+ request);
        InfluencerWithUserNameDTO influencerWithUserNameDTO =
                influencerService.findAllInfluencerById(request.getInfluencerId(), request.getInfluencerName());

        CollaborationRequestDto collaborationRequestDto = new CollaborationRequestDto();
        collaborationRequestDto.setInfluencerWithUserNameDTO(influencerWithUserNameDTO);
        collaborationRequestDto.setCollaborationDto(influencerService.findCollaboration(userId, request.getInfluencerId()));
        return collaborationRequestDto;
    }


    @GetMapping("/check")
    public String test(){
        return "Thanks";
    }
}
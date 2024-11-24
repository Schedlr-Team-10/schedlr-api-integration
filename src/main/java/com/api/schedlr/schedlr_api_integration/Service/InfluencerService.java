package com.api.schedlr.schedlr_api_integration.Service;

import com.api.schedlr.schedlr_api_integration.DTOs.CollaborationDto;
import com.api.schedlr.schedlr_api_integration.DTOs.InfluencerWithUserNameDTO;
import com.api.schedlr.schedlr_api_integration.DTOs.InfluencersList;
import com.api.schedlr.schedlr_api_integration.entity.Collaboration;
import com.api.schedlr.schedlr_api_integration.entity.Influencer;
import com.api.schedlr.schedlr_api_integration.entity.User;
import com.api.schedlr.schedlr_api_integration.repo.CollaborationRepositoy;
import com.api.schedlr.schedlr_api_integration.repo.InfluencerRepository;
import com.api.schedlr.schedlr_api_integration.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InfluencerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InfluencerRepository influencerRepository;

    @Autowired
    CollaborationRepositoy collaborationRepositoy;

    public Influencer getInfluencersByUserId(int userid) {
        return influencerRepository.findByUserid(userid);
    }

    public Influencer saveInfluencer(Influencer influencer) {
        return influencerRepository.save(influencer);
    }

    public List<InfluencerWithUserNameDTO> findAllInfluencers() {
         return addNameToList(influencerRepository.findAll());
    }

    public List<InfluencerWithUserNameDTO> addNameToList(List<Influencer> influencersList){

        List<InfluencerWithUserNameDTO> influencerWithUserNameDTOSList = new ArrayList<>();
        influencersList.forEach(influencer -> {

            Optional<User> user = userRepository.findByUserid(influencer.getUserid());
            String name = user.map(User::getUsername).orElse("Default Username");
            InfluencerWithUserNameDTO influencerWithUserNameDTO = new InfluencerWithUserNameDTO(influencer.getInfluencerId(),
                    influencer.getProfilePic(), influencer.getBio(), influencer.getLinkedinFollowers(),
                    influencer.getPinterestFollowers(), influencer.getTwitterFollowers(), influencer.getLinkedinProfile(),
                    influencer.getPinterestProfile(), influencer.getTwitterProfile(), influencer.getPricePerPhoto(),
                    influencer.getPricePerVideo(), influencer.getPricePerTweet(), influencer.getTags(), name );
            influencerWithUserNameDTOSList.add(influencerWithUserNameDTO);
        });
        return influencerWithUserNameDTOSList;
    }

    public InfluencerWithUserNameDTO convertInflucnerWithUserDto(Influencer influencer, String name){
        InfluencerWithUserNameDTO influencerWithUserNameDTO = new InfluencerWithUserNameDTO(influencer.getInfluencerId(),
                influencer.getProfilePic(), influencer.getBio(), influencer.getLinkedinFollowers(),
                influencer.getPinterestFollowers(), influencer.getTwitterFollowers(), influencer.getLinkedinProfile(),
                influencer.getPinterestProfile(), influencer.getTwitterProfile(), influencer.getPricePerPhoto(),
                influencer.getPricePerVideo(), influencer.getPricePerTweet(), influencer.getTags(), name );
        return influencerWithUserNameDTO;
    }

    public List<User> getInfluencersList(){
        return userRepository.findAllInfluencers();
    }

    public InfluencerWithUserNameDTO findAllInfluencerById(int userid, String name) {
        Influencer influencer =  influencerRepository.findByUserid(userid);
        return convertInflucnerWithUserDto(influencer, name);
    }

    public CollaborationDto findCollaboration(int userId, int influencerId){
        try {

            Collaboration collaboration = collaborationRepositoy
                    .findByUserIdAndInfluencerId(userId, influencerId)
                    .orElseThrow(() -> new RuntimeException("Collaboration not found for userId: "
                            + userId + " and influencerId: " + influencerId));
            CollaborationDto collaborationDto = new CollaborationDto();
            collaborationDto.setId(collaboration.getId());
            collaborationDto.setInfluencerId(collaboration.getInfluencerId());
            collaborationDto.setUserId(collaboration.getUserId());
            collaborationDto.setMessage(collaboration.getMessage());
            collaborationDto.setStatus(collaboration.getStatus());
            collaborationDto.setCollaborationToken(collaboration.getCollaborationToken());
            return collaborationDto;
        }catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}

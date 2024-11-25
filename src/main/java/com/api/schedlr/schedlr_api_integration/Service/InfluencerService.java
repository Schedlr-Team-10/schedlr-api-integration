package com.api.schedlr.schedlr_api_integration.Service;

import com.api.schedlr.schedlr_api_integration.DTOs.CollaborationDto;
import com.api.schedlr.schedlr_api_integration.DTOs.CollaborationWithUsernameDTO;
import com.api.schedlr.schedlr_api_integration.DTOs.InfluencerWithUserNameDTO;
import com.api.schedlr.schedlr_api_integration.entity.Collaboration;
import com.api.schedlr.schedlr_api_integration.entity.Influencer;
import com.api.schedlr.schedlr_api_integration.entity.User;
import com.api.schedlr.schedlr_api_integration.repo.CollaborationRepository;
import com.api.schedlr.schedlr_api_integration.repo.InfluencerRepository;
import com.api.schedlr.schedlr_api_integration.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
public class InfluencerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InfluencerRepository influencerRepository;

    @Autowired
    CollaborationRepository collaborationRepository;

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

            Collaboration collaboration = collaborationRepository
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

    public Collaboration updateCollaborationStatus(int userId, int influencerId, String status) {
        Optional<Collaboration> collaborationOpt = collaborationRepository.findByUserIdAndInfluencerId(userId, influencerId);

        if (collaborationOpt.isPresent()) {
            Collaboration collaboration = collaborationOpt.get();
            log.info("Status : ", Collaboration.Status.valueOf(status));
            if(Collaboration.Status.valueOf(status).equals(Collaboration.Status.COMPLETED)){
                collaboration.setCollaborationToken(generateToken(userId, influencerId));
            }
            collaboration.setStatus(Collaboration.Status.valueOf(status));
            return collaborationRepository.save(collaboration);
        } else {
            throw new RuntimeException("Collaboration not found for userId: " + userId + " and influencerId: " + influencerId);
        }
    }

    public Collaboration raiseCollaborationRequest(int userId, int influencerId, String message) {
        Collaboration collaboration = new Collaboration();
        collaboration.setUserId(userId);
        collaboration.setInfluencerId(influencerId);
        collaboration.setMessage(message);
        collaboration.setStatus(Collaboration.Status.PENDING);
        collaboration.setCollaborationToken("");
        return collaborationRepository.save(collaboration);
    }

    public static String generateToken(int userId, int influencerId) {
        // Combine userId, influencerId, and a UUID for randomness
        String input = userId + "-" + influencerId + "-" + UUID.randomUUID().toString();

        try {
            // Use SHA-256 to hash the input
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Encode the hash in Base64 to get a readable token
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    @Transactional
    public void deleteCollaboration(int userId, int influencerId) {
        collaborationRepository.deleteByUserIdAndInfluencerId(userId, influencerId);
    }

    public List<CollaborationWithUsernameDTO> getPendingCollaborationRequestsWithUsernames(int influencerId) {
        List<Object[]> results = collaborationRepository.findByInfluencerIdAndStatusWithUsernames(
                influencerId);

        // Map results to DTO
        return results.stream()
                .map(result -> new CollaborationWithUsernameDTO(
                        (Collaboration) result[0],
                        (String) result[1]
                ))
                .toList();
    }
}
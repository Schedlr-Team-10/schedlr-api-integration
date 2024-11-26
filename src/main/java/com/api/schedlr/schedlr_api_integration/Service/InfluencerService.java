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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class InfluencerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InfluencerRepository influencerRepository;

    @Autowired
    CollaborationRepository collaborationRepository;



    public Influencer saveInfluencer(Influencer influencer) {
        return influencerRepository.save(influencer);
    }

    public InfluencerWithUserNameDTO convertInflucnerWithUserDto(Influencer influencer, String name){
        InfluencerWithUserNameDTO influencerWithUserNameDTO = new InfluencerWithUserNameDTO(influencer.getId(),
                influencer.getLinkedinProfile(),
                influencer.getPinterestProfile(), influencer.getTwitterProfile(), influencer.getPricePerPhoto(),
                influencer.getTags(), name );
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
            }else {
                collaboration.setCollaborationToken(null);
            }
            collaboration.setStatus(Collaboration.Status.valueOf(status));
            return collaborationRepository.save(collaboration);
        } else {
            throw new RuntimeException("Collaboration not found for userId: " + userId + " and influencerId: " + influencerId);
        }
    }

    public Collaboration raiseCollaborationRequest(int userId, int influencerId, String message) {
        Optional<Collaboration> existingCollaboration = collaborationRepository.findByUserIdAndInfluencerId(userId, influencerId);

        if (existingCollaboration.isPresent()) {
            // If collaboration exists, return it (or throw an exception if needed)
            return existingCollaboration.get();
        }

        Collaboration collaboration = new Collaboration();
        collaboration.setUserId(userId);
        collaboration.setInfluencerId(influencerId);
        collaboration.setMessage(message);
        collaboration.setStatus(Collaboration.Status.PENDING);
        collaboration.setCollaborationToken(null);
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

    public Influencer saveOrUpdateInfluencerProfile(int userid, String linkedinProfile,
                                                    String pinterestProfile, String twitterProfile,
                                                    int pricePerPhoto) {

        Optional<Influencer> influencerOpt = Optional.ofNullable(influencerRepository.findByUserid(userid));

        Influencer influencer;

        if (influencerOpt.isPresent()) {
            influencer = influencerOpt.get();
            influencer.setLinkedinProfile(linkedinProfile);
            influencer.setPinterestProfile(pinterestProfile);
            influencer.setTwitterProfile(twitterProfile);
            influencer.setPricePerPhoto(pricePerPhoto);
            influencer.setTags(null);

            log.info("Updating existing Influencer with userid: {}", userid);
        } else {
            influencer = new Influencer();
            influencer.setUserid(userid);
            influencer.setLinkedinProfile(linkedinProfile);
            influencer.setPinterestProfile(pinterestProfile);
            influencer.setTwitterProfile(twitterProfile);
            influencer.setPricePerPhoto(pricePerPhoto);
            influencer.setTags(null);

            log.info("Creating new Influencer with userid: {}", userid);
        }

        Influencer savedInfluencer = influencerRepository.save(influencer);
        return savedInfluencer;
    }

    public Integer getInfluencerIdByToken(String collaborationToken) {
        return collaborationRepository.findInfluencerIdByCollaborationToken(collaborationToken)
                .orElseThrow(() -> new NoSuchElementException("No collaboration found for token: " + collaborationToken));
    }

}
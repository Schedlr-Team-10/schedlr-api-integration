package com.api.schedlr.schedlr_api_integration.Service;

import com.api.schedlr.schedlr_api_integration.Constants.APIConstants;
import com.api.schedlr.schedlr_api_integration.entity.Profile;
import com.api.schedlr.schedlr_api_integration.entity.User;
import com.api.schedlr.schedlr_api_integration.repo.ProfileRepository;
import com.api.schedlr.schedlr_api_integration.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PlatformService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public Profile updateLinkedInAccessToken(int userId, String linkedInAccessToken, String linkedInPersonId) {
        // Fetch the User entity using userId
        Optional<User> userOptional = userRepository.findById(userId);
        // Check if the user exists
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found.");
        }

        // Get the User object
        User user = userOptional.get();

        // Get current date and add 45 days
        LocalDateTime linkedInAccessTokenExpireDate = LocalDateTime.now().plusDays(45);

        Optional<Profile> existingProfile = profileRepository.findByUserId(userId);
        // If profile exists, update the LinkedIn-related fields if they are null
        if (existingProfile.isPresent()) {
            Profile profile = existingProfile.get();

            // Only update if the fields are currently null

            profile.setLinkedInAccessToken(linkedInAccessToken);
            profile.setLinkedInAccessTokenExpireDate(linkedInAccessTokenExpireDate);
            profile.setLinkedInPersonId(linkedInPersonId);

            // Save the updated profile
            return profileRepository.save(profile);
        } else {
            // If profile doesn't exist, create a new one
            Profile newProfile = new Profile();
            newProfile.setUserId(user.getUserid());  // Set the User object
            newProfile.setLinkedInAccessToken(linkedInAccessToken);
            newProfile.setLinkedInAccessTokenExpireDate(linkedInAccessTokenExpireDate);
            newProfile.setLinkedInPersonId(linkedInPersonId);
            // Save the new profile
            return profileRepository.save(newProfile);
        }
    }

    public String getAccessToken(int userId, String code, String state) {

        String tokenUrl = APIConstants.LINKEDIN_GET_ACCESS_TOKEN;

        // Prepare the form data
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        log.info("Code : "+code);
        body.add("code", code);
        body.add("redirect_uri", APIConstants.LINKEDIN_REDIRECT_URL);
        body.add("client_id", APIConstants.CLIENT_ID);
        body.add("client_secret", APIConstants.CLIENT_SECRET);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create HTTP request entity with body and headers
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // Send POST request to LinkedIn to exchange code for access token
            ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, Map.class);

            // Extract access token from the response
            String accessToken = (String) response.getBody().get("access_token");
            log.info("Got it : "+accessToken);

            String personId = getPersonId(accessToken);
            log.info("PersonId: "+personId);
            Profile profile=null;
            if(!personId.startsWith("{"));
            {
                log.info("updating");
                profile = updateLinkedInAccessToken(userId, accessToken, personId);
            }

            return (profile!=null)? "Success" : "Failed";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }

    public String getPersonId(String accessToken) {
        String url = APIConstants.LINKEDIN_GET_PERSON_ID;

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("X-Restli-Protocol-Version", "2.0.0");

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in PersonId method....");
        try {
            // Send the GET request with the headers and capture the response
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Parse the response body into a Map
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
            log.info("Response : "+ responseBody);
            // Extract the "id" value (not "sub")
            String personId = responseBody.get("sub").toString(); // Adjust this based on the actual response

            return personId;

        } catch (HttpClientErrorException e) {
            // Log and handle specific error responses
            System.err.println("Error fetching person ID: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getPInterestAccessToken(String userId, String code) {
        String clientId = APIConstants.PINTEREST_CLIENT_ID;
        String clientSecret = APIConstants.PINTEREST_CLIENT_SECRET;
        String redirectUri = APIConstants.PINTEREST_REDIRECT_URL;

        try {
            // Encode client_id and client_secret for Basic Auth
            String auth = clientId + ":" + clientSecret;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeader = "Basic " + encodedAuth;

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", authHeader);
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            // Set up the request body
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", "authorization_code");
            requestBody.add("code", code);
            requestBody.add("redirect_uri", redirectUri);

            // Create the HttpEntity with headers and body
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Initialize RestTemplate and send POST request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://api.pinterest.com/v5/oauth/token",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // Handle response
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("PInterest Access Token: "+ response.getBody().get("access_token"));
                updatePInterestAccessToken(userId, (String) response.getBody().get("access_token"));
                return (String)response.getBody().get("access_token");
            } else {
                // Handle non-200 responses
                return "Error fetching access token: " + response.getStatusCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching access token";
        }
    }

    public Profile updatePInterestAccessToken(String userId, String pInterestAccessToken) {
        // Fetch the User entity using userId
        Optional<User> userOptional = userRepository.findById(Integer.parseInt(userId));
        // Check if the user exists
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found.");
        }

        // Get the User object
        User user = userOptional.get();

        // Get current date and add 45 days
        LocalDateTime pinterestAccessTokenExpireDate = LocalDateTime.now().plusDays(30);

        Optional<Profile> existingProfile = profileRepository.findByUserId(Integer.parseInt(userId));
        // If profile exists, update the LinkedIn-related fields if they are null
        if (existingProfile.isPresent()) {
            Profile profile = existingProfile.get();

            // Only update if the fields are currently null

            profile.setPinterestAccessToken(pInterestAccessToken);
            profile.setPinterestAccessTokenExpireDate(pinterestAccessTokenExpireDate);

            // Save the updated profile
            return profileRepository.save(profile);
        } else {
            // If profile doesn't exist, create a new one
            Profile newProfile = new Profile();
            newProfile.setUserId(user.getUserid());  // Set the User object
            newProfile.setPinterestAccessToken(pInterestAccessToken);
            newProfile.setPinterestAccessTokenExpireDate(pinterestAccessTokenExpireDate);
            // Save the new profile
            return profileRepository.save(newProfile);
        }
    }
}

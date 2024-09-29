package com.api.schedlr.schedlr_api_integration.Service;
import com.api.schedlr.schedlr_api_integration.Constants.APIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.Map;

@Service
public class LinkedinService {

    @Autowired
    RestTemplate restTemplate;


    public ResponseEntity<String> uploadImage(String uploadUrl, String filePath) {
        try {
            FileSystemResource resource = new FileSystemResource(new File(filePath));
            System.out.println(resource);
            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(APIConstants.ClIENT_ACCESS_TOKEN);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // Create HttpEntity with the file data
            HttpEntity<FileSystemResource> requestEntity = new HttpEntity<>(resource, headers);

            // Execute the request
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAccessToken(String code, String state) {
        String tokenUrl = "https://www.linkedin.com/oauth/v2/accessToken";

        // Prepare the form data
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
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
            System.out.println("Got it : "+accessToken);
            // Return access token to the client
            return ResponseEntity.ok().body(Map.of("access_token", accessToken));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to exchange authorization code for access token");
        }
    }
}

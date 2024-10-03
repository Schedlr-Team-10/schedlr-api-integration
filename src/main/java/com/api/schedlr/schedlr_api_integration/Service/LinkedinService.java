package com.api.schedlr.schedlr_api_integration.Service;
import com.api.schedlr.schedlr_api_integration.DTOs.LinkedInData;
import com.api.schedlr.schedlr_api_integration.repo.ProfileRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient; // Use classes from Apache HttpClient 5.x
import org.apache.hc.client5.http.impl.classic.HttpClients;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LinkedinService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProfileRepository profileRepository;

    public String uploadPostLinkedIn(int userId, MultipartFile image, String postDescription){
        System.out.println("Called uploadPostLinkedIn");
        Optional<LinkedInData> data = getLinkedInAccessTokenAndPersonId(userId);
        if(data==null){
           return "Failed to retreive from DB";
        }
        String accessToken = data.get().getAccessToken();
        String personId = data.get().getPersonId();

        Map<String, String> uploadImageJson = registerUpload(accessToken, personId);

        if(uploadImageJson==null){
            return "Failed to upload Image in the url";
        }

        String asset=uploadImageJson.get("asset");
        String uploadUrl=uploadImageJson.get("uploadUrl");

        uploadImage(uploadUrl, image, accessToken);

        createShare(accessToken, asset, String.valueOf(userId), postDescription);


        return "Ok";
    }

    public Optional<LinkedInData> getLinkedInAccessTokenAndPersonId(int userId) {
        List<Object[]> listOfRows= profileRepository.findLinkedInTokenAndPersonIdByUserId(userId);
        if (listOfRows.size()!=0) {
            Object[] data = listOfRows.get(0);
            String accessToken = (String) data[0];
            String personId = (String) data[1];
            return Optional.of(new LinkedInData(accessToken, personId));
        }
        return Optional.empty();
    }

    public Map<String, String> registerUpload(String accessToken, String personId) {
        String url = "https://api.linkedin.com/v2/assets?action=registerUpload";

        try {
            // Create the dynamic JSON request body
            String requestBody = createRegisterUploadRequestJson(personId);

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.set("Content-Type", "application/json");

            // Create an HttpEntity with the headers and body
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Send the POST request
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // Parse the response body
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseBody = objectMapper.readTree(response.getBody());

            // Extract asset and uploadUrl values
            String asset = responseBody.path("value").path("asset").asText();
            String uploadUrl = responseBody.path("value")
                    .path("uploadMechanism")
                    .path("com.linkedin.digitalmedia.uploading.MediaUploadHttpRequest")
                    .path("uploadUrl").asText();

            // Return asset and uploadUrl in a Map
            Map<String, String> result = new HashMap<>();
            result.put("asset", asset);
            result.put("uploadUrl", uploadUrl);

            System.out.println("uploadUrl : "+ uploadUrl);
            System.out.println("asset : "+ asset);

            return result;

        } catch (HttpClientErrorException e) {
            // Handle error responses (e.g., 401 Unauthorized, 403 Forbidden, etc.)
            throw new RuntimeException("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private String createRegisterUploadRequestJson(String personId) throws Exception {
        // Create the JSON structure using a Map
        Map<String, Object> requestBody = new HashMap<>();

        Map<String, Object> registerUploadRequest = new HashMap<>();
        registerUploadRequest.put("recipes", new String[]{"urn:li:digitalmediaRecipe:feedshare-image"});
        registerUploadRequest.put("owner", "urn:li:person:" + personId);

        Map<String, Object> serviceRelationship = new HashMap<>();
        serviceRelationship.put("relationshipType", "OWNER");
        serviceRelationship.put("identifier", "urn:li:userGeneratedContent");

        registerUploadRequest.put("serviceRelationships", new Object[]{serviceRelationship});

        requestBody.put("registerUploadRequest", registerUploadRequest);

        // Convert the Map to JSON using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestBody);
    }

    public ResponseEntity<String> uploadImage(String uploadUrl, MultipartFile imageFile, String accessToken) {
        try {

            URI uri = UriComponentsBuilder.fromUriString(uploadUrl).build().toUri();

            File tempFile = File.createTempFile("upload", imageFile.getOriginalFilename());
            imageFile.transferTo(tempFile);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            HttpEntity<FileSystemResource> requestEntity = new HttpEntity<>(new FileSystemResource(tempFile), headers);
            System.out.println("Called uploadImage 2 : "+ uri);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

            System.out.println("Upload Response: " + response);

            boolean isDeleted = tempFile.delete();
            if (!isDeleted) {
                System.out.println("Temporary file deletion failed: " + tempFile.getAbsolutePath());
            }

            return response;
        } catch (IOException e) {
            System.err.println("File processing failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing failed");
        } catch (Exception e) {
            System.err.println("File upload failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }

    public ResponseEntity<String> createShare(String accessToken, String asset, String userId, String commentary) {
        String url = "https://api.linkedin.com/v2/ugcPosts"; // Replace with your actual API URL

        try {
            // Create the request body
            Map<String, Object> shareBody = new HashMap<>();
            shareBody.put("author", "urn:li:person:" + userId);
            shareBody.put("lifecycleState", "PUBLISHED");

            Map<String, Object> shareContent = new HashMap<>();
            Map<String, Object> shareCommentary = new HashMap<>();
            shareCommentary.put("text", commentary);
            shareContent.put("shareCommentary", shareCommentary);
            shareContent.put("shareMediaCategory", "IMAGE");

            // Add media details to the request
            Map<String, Object> media = new HashMap<>();
            media.put("status", "READY");
            media.put("media", asset);
            shareContent.put("media", new Map[]{media});

            Map<String, Object> specificContent = new HashMap<>();
            specificContent.put("com.linkedin.ugc.ShareContent", shareContent);
            shareBody.put("specificContent", specificContent);

            // Set visibility to public
            Map<String, Object> visibility = new HashMap<>();
            visibility.put("com.linkedin.ugc.MemberNetworkVisibility", "PUBLIC");
            shareBody.put("visibility", visibility);

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HttpEntity with headers and body
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(shareBody, headers);

            // Configure RestTemplate to use HttpComponentsClientHttpRequestFactory
            RestTemplate restTemplate = getCustomRestTemplate();

            // Send the POST request
            return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Share creation failed");
        }
    }

    public RestTemplate getCustomRestTemplate() {
        CloseableHttpClient httpClient = HttpClients.createDefault(); // Apache HttpClient 5.x

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }
}

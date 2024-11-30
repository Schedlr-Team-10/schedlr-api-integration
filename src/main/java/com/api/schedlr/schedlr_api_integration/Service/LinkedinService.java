package com.api.schedlr.schedlr_api_integration.Service;
import com.api.schedlr.schedlr_api_integration.Constants.APIConstants;
import com.api.schedlr.schedlr_api_integration.DTOs.LinkedInData;
import com.api.schedlr.schedlr_api_integration.repo.ProfileRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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




import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class LinkedinService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProfileRepository profileRepository;

    public String uploadPostLinkedIn(int userId, MultipartFile image, String postDescription){
        log.info("Called uploadPostLinkedIn");
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

        postUGC(accessToken, personId, asset, postDescription, "mediaDescription", "mediaTitle");

        return "Ok";
    }

    public Optional<LinkedInData> getLinkedInAccessTokenAndPersonId(int userId) {
        log.info("Inside getLinkedInAccessTokenAndPersonId Method");
        List<Object[]> listOfRows= profileRepository.findLinkedInTokenAndPersonIdByUserId(userId);
        if (listOfRows.size()!=0) {
            Object[] data = listOfRows.get(0);
            String accessToken = (String) data[0];
            String personId = (String) data[1];
            return Optional.of(new LinkedInData(accessToken, personId));
        }
        log.info("End of getLinkedInAccessTokenAndPersonId Method");
        return Optional.empty();
    }

    public Map<String, String> registerUpload(String accessToken, String personId) {
        log.info("Inside registerUpload method");
        String url = APIConstants.LINKEDIN_REGISTER_UPLOAD_IMAGE;

        try {
            // Create the dynamic JSON request body
            String requestBody = createRegisterUploadRequestJson(personId);

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.set("Content-Type", "application/json");

            // Create an HttpEntity with the headers and body
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            log.info("Calling the registerUpload Url to get the uploadUrl and asset");
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

            log.info("uploadUrl : "+ uploadUrl);
            log.info("asset : "+ asset);
            log.info("Got the UploadUrl and asset...!!");
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
            log.info("Called uploadImage 2 : "+ uri);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

            log.info("Upload Response: " + response);

            boolean isDeleted = tempFile.delete();
            if (!isDeleted) {
                log.info("Temporary file deletion failed: " + tempFile.getAbsolutePath());
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

    public String postUGC(String accessToken, String personUrn, String mediaUrn, String postText, String mediaDescription, String mediaTitle) {
        log.info("AccessToken: " + accessToken);
        log.info("PersonID: " + personUrn);
        log.info("Asset: " + mediaUrn);
        log.info("PostText: " + postText);
        log.info("MediaDescription: " + mediaDescription);
        log.info("MediaTitle: " + mediaTitle);

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Restli-Protocol-Version", "2.0.0");
        headers.setBearerAuth(accessToken);

        // Create body with proper JSON serialization
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("author", "urn:li:person:" + personUrn);
        requestBody.put("lifecycleState", "PUBLISHED");

        Map<String, Object> specificContent = new HashMap<>();
        Map<String, Object> shareCommentary = new HashMap<>();
        shareCommentary.put("text", postText);

        Map<String, Object> mediaData = new HashMap<>();
        mediaData.put("status", "READY");

        Map<String, Object> description = new HashMap<>();
        description.put("text", mediaDescription);

        mediaData.put("description", description);
        mediaData.put("media", mediaUrn);
        Map<String, Object> title = new HashMap<>();
        title.put("text", mediaTitle);
        mediaData.put("title", title);

        specificContent.put("com.linkedin.ugc.ShareContent", Map.of(
                "shareCommentary", shareCommentary,
                "shareMediaCategory", "IMAGE",
                "media", new Object[] { mediaData }
        ));

        requestBody.put("specificContent", specificContent);
        requestBody.put("visibility", Map.of(
                "com.linkedin.ugc.MemberNetworkVisibility", "PUBLIC"
        ));

        try {
            // Serialize requestBody to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            // Create request entity with JSON string
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            // Send request
            RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
            ResponseEntity<String> response = restTemplate.exchange(
                    APIConstants.LINKEDIN_POST_IMAGE,
                    HttpMethod.POST,
                    request,
                    String.class
            );
            log.info("Response Body : "+response.getBody());
            return response.getBody();
        } catch (Exception e) {
            // Handle and log error
            System.err.println("Error occurred while posting to LinkedIn: " + e.getMessage());
            throw new RuntimeException("Failed to post UGC", e);
        }
    }
}

package com.api.schedlr.schedlr_api_integration.Service;

import com.api.schedlr.schedlr_api_integration.Constants.APIConstants;
import com.api.schedlr.schedlr_api_integration.DTOs.PinterestPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PInterestService {

    @Autowired
    RestTemplate restTemplate;

    public String convertToBase64(MultipartFile file) {
        try {
            // Convert the file content to a byte array
            byte[] bytes = file.getBytes();
            // Encode the byte array to a Base64 string
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert image to Base64", e);
        }
    }

    public String createPin(MultipartFile file, String description, String userId) {
        String url = APIConstants.PINTEREST_SANDBOX_POSTURL;

        String imageBase64 = convertToBase64(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("link", "https://your-website.com");
        requestBody.put("title", "My First Pin");
        requestBody.put("description", description);
        requestBody.put("dominant_color", "#FF5733");
        requestBody.put("alt_text", "A beautiful sunset over a lake");
        requestBody.put("board_id", "943996840583492057");

        Map<String, String> mediaSource = new HashMap<>();
        mediaSource.put("source_type", "image_base64");
        mediaSource.put("content_type", "image/jpeg");
        mediaSource.put("data", imageBase64);

        requestBody.put("media_source", mediaSource);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Send the POST request and capture the response
        ResponseEntity<PinterestPostResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, PinterestPostResponse.class);

        // Retrieve and store the 'id' from the response
        if (response.getBody() != null) {
            String postId = response.getBody().getId();
            log.info("Post ID: " + postId);
            return postId;
        }

        return null;
    }

}

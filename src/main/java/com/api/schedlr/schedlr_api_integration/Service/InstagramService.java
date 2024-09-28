package com.api.schedlr.schedlr_api_integration.Service;

import com.api.schedlr.schedlr_api_integration.Constants.APIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class InstagramService {

    @Autowired
    RestTemplate restTemplate;

    public String uploadImage(MultipartFile fileName, String fileType) {
        long fileLength = fileName.getSize();
        String appId = APIConstants.appId;
        String accessToken = APIConstants.token;
        // URL for the API request
        String url = "https://graph.facebook.com/v20.0/" + appId + "/uploads"
                + "?file_name=" + fileName
                + "&file_length=" + fileLength
                + "&file_type=" + fileType
                + "&access_token=" + accessToken;

        // Set headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return response.getBody(); // Returns response from Instagram API
    }
}

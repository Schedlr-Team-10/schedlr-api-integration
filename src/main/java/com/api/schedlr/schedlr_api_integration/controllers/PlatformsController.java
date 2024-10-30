package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.Service.LinkedinService;
import com.api.schedlr.schedlr_api_integration.Service.PInterestService;
import com.api.schedlr.schedlr_api_integration.Service.PlatformService;
import com.api.schedlr.schedlr_api_integration.entity.PostUpload;
import com.api.schedlr.schedlr_api_integration.repo.PostUploadRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedlr")
public class PlatformsController {

    @Autowired
    PlatformService platformService;

    @Autowired
    LinkedinService linkedinService;

    @Autowired
    PInterestService pInterestService;


    @Autowired
    private PostUploadRepository postUploadRepository;

    @PostMapping("/postupload")
    public ResponseEntity<String> uploadImage(@RequestParam("userId") String userId,
                              @RequestParam("uploadImage") MultipartFile uploadImage,
                              @RequestParam("description") String description,
                              @RequestParam("platforms") String platformsJson) throws IOException, JSONException {

        JSONArray jsonArray = new JSONArray(platformsJson);
        List<String> platforms = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            platforms.add(jsonArray.getString(i));
        }
        byte[] imageBytes = uploadImage.getBytes();

        PostUpload postUpload = new PostUpload();
        postUpload.setUserId(Integer.valueOf(userId));

        try {
            log.info("Setting image in the postupload");
            postUpload.setImage(uploadImage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Setting description in the postupload");
        postUpload.setDescription(description);

        log.info("Setting date in the postupload");
        postUpload.setPostUploadDate(LocalDateTime.now());

        log.info("checking the condition");
        if (platforms.contains("LinkedIn")) {
            log.info("Calling linkedin service method");
            String postId = linkedinService.uploadPostLinkedIn(Integer.parseInt(userId), new MockMultipartFile(uploadImage.getName(), imageBytes), description);
            postUpload.setLinkedinPostId(postId);
        }
        if (platforms.contains("PInterest")) {
            String postId = pInterestService.createPin(new MockMultipartFile(uploadImage.getName(), imageBytes), description, String.valueOf(userId));
            postUpload.setPinterestPostId(postId);
        }
        if (platforms.contains("Twitter")) {

        }
        postUploadRepository.save(postUpload);
        return null;
    }

    @GetMapping("/health2")
    public String getUserPosts() {
        return "Good2";
    }
    @GetMapping("/posthistory")
    public ResponseEntity<List<PostUpload>> getUserPosts(@RequestParam("userId") Integer userId) {
        List<PostUpload> posts = postUploadRepository.findByUserId(userId);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no posts found
        }
        return ResponseEntity.ok(posts); // Return the list of posts
    }

}

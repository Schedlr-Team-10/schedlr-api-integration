package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.Service.LinkedinService;
import com.api.schedlr.schedlr_api_integration.Service.PInterestService;
import com.api.schedlr.schedlr_api_integration.Service.PlatformService;
import com.api.schedlr.schedlr_api_integration.entity.PostUpload;
import com.api.schedlr.schedlr_api_integration.entity.SchedulePostUpload;
import com.api.schedlr.schedlr_api_integration.entity.User;
import com.api.schedlr.schedlr_api_integration.repo.PostUploadRepository;
import com.api.schedlr.schedlr_api_integration.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/postupload")
    public ResponseEntity<String> uploadImage(@RequestParam("userId") String userId,
                              @RequestParam("uploadImage") MultipartFile uploadImage,
                              @RequestParam("description") String description,
                              @RequestParam("platforms") String platformsJson) throws IOException, JSONException {

        JSONArray jsonArray = new JSONArray(platformsJson);
        log.info("JSON of the platforms are : "+ platformsJson);
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
        log.info("platforms selected : "+ platforms);
        if (platforms.contains("PInterest")) {
            log.info("Calling the pinterest service method...!!!");
            String postId = pInterestService.createPin(new MockMultipartFile(uploadImage.getName(), imageBytes), description, String.valueOf(userId));
            postUpload.setPinterestPostId(postId);
        }
        if (platforms.contains("Twitter")) {

        }
        postUploadRepository.save(postUpload);
        return null;
    }

    @GetMapping("/health2")
    public ResponseEntity<String>  getUserPosts() {
        System.out.println("Calling the health");
        return null;
    }
    @GetMapping("/posthistory")
    public ResponseEntity<List<PostUpload>> getUserPosts(@RequestParam("userId") Integer userId) {
        List<PostUpload> posts = postUploadRepository.findByUserId(userId);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no posts found
        }
        return ResponseEntity.ok(posts); // Return the list of posts
    }

    @PostMapping("/schedule")
    public ResponseEntity<String> schedule(@RequestParam("userId") String userId,
                                           @RequestParam("uploadImage") MultipartFile uploadImage,
                                           @RequestParam("description") String description,
                                           @RequestParam("platforms") String platformsJson,
                                           @RequestParam("scheduleTime") String time) {
        log.info("Calling the schedule API with schedule time: {}", time);

        SchedulePostUpload post = new SchedulePostUpload();

        // Parse the schedule time
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
            post.setScheduleTime(dateTime);
        } catch (Exception e) {
            log.error("Invalid schedule time format: {}", time, e);
            return ResponseEntity.badRequest().body("Invalid schedule time format.");
        }

        // Parse the platforms JSON
        List<String> platforms = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(platformsJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                platforms.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            log.error("Invalid platforms JSON: {}", platformsJson, e);
            return ResponseEntity.badRequest().body("Invalid platforms JSON.");
        }

        // Set the User object
        try {
            int userIdInt = Integer.parseInt(userId);
            Optional<User> userOptional = userRepository.findByUserid(userIdInt);
            if (userOptional.isPresent()) {
                User user = userOptional.get(); // Get the User object
                post.setUser(user); // Set the User object in the post
            } else {
                return ResponseEntity.badRequest().body("User not found.");
            }
        } catch (Exception e) {
            log.error("Failed to fetch or set the User object for userId: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to associate the user.");
        }

        // Set other post details
        post.setDescription(description);

        try {
            post.setImage(uploadImage.getBytes());
        } catch (IOException e) {
            log.error("Failed to process the uploaded image.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the uploaded image.");
        }

        // Set platform flags
        post.setLinkedin(platforms.contains("LinkedIn"));
        post.setPinterest(platforms.contains("PInterest"));
        post.setTwitter(platforms.contains("Twitter"));
        post.setFb(platforms.contains("Facebook"));

        // Save post
        try {
            SchedulePostUpload savedPost = platformService.schedulePost(post);
            if (savedPost == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to schedule the post.");
            }
        } catch (Exception e) {
            log.error("Error saving the scheduled post.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while scheduling the post.");
        }

        return ResponseEntity.ok("Post scheduled successfully.");
    }



}

package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.DTOs.UploadPost;
import com.api.schedlr.schedlr_api_integration.Service.LinkedinService;
import com.api.schedlr.schedlr_api_integration.Service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/linkedin")
public class LinkedinController {

    @Autowired
    LinkedinService linkedinService;

    @Autowired
    PlatformService platformService;

    @GetMapping("/health")
    public String health(){
        return "Thanks Linkedin";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/authCode")
    public String handleLinkedInAuthCode(@RequestBody Map<String, String> requestBody) {
        String code = requestBody.get("code");
        String state = requestBody.get("state");
        int userId = Integer.parseInt(requestBody.get("userId"));
        return platformService.getAccessToken(userId,code,state);

    }

    {

        "image" : imgae.png,
            "description" : "asdfasdf",
            "userId" : "121"
    }

    @PostMapping("/uploadPost")
    public String uploadPost(@RequestBody UploadPost uploadPost){
//@RequestBody UploadPost uploadPost
        String filePath = "src/main/java/com/api/schedlr/schedlr_api_integration/images/Groot.jpeg";
        return linkedinService.uploadPostLinkedIn(1,null,"as");

    }

    @GetMapping("/upload-image")
    public String uploadImage() throws IOException {
        String uploadUrl = "https://www.linkedin.com/dms-uploads/sp/D5622AQGv3PI-R6CH5g/uploaded-image/0?ca=vector_feedshare&cn=uploads&sync=0&v=beta&ut=2JXjezH1o4nbs1";  // Replace with the URL received from step 1
        String filePath = "src/main/java/com/api/schedlr/schedlr_api_integration/images/Groot.jpeg";  // Path to the file on your local system
        String description = "Posting an image in linkedin using my application. I'm happy if it works..!!";
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);

        // Create MultipartFile using MockMultipartFile
        MultipartFile image = new MockMultipartFile(file.getName(), file.getName(), filePath, input);
        return linkedinService.uploadPostLinkedIn(1, image, description);
    }






}

package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.Service.LinkedinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/linkedin")
public class LinkedinController {

    @Autowired
    LinkedinService linkedinService;

    @GetMapping("/health")
    public String health(){
        return "Thanks Linkedin";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/authCode")
    public ResponseEntity<?> handleLinkedInAuthCode(@RequestBody Map<String, String> requestBody) {
        String code = requestBody.get("code");
        String state = requestBody.get("state");
        return linkedinService.getAccessToken(code,state);

    }


    @GetMapping("/upload-image")
    public ResponseEntity<String> uploadImage() {
        String uploadUrl = "https://www.linkedin.com/dms-uploads/sp/D5622AQGv3PI-R6CH5g/uploaded-image/0?ca=vector_feedshare&cn=uploads&sync=0&v=beta&ut=2JXjezH1o4nbs1";  // Replace with the URL received from step 1
        String filePath = "src/main/java/com/api/schedlr/schedlr_api_integration/images/Groot.jpeg";  // Path to the file on your local system

        return linkedinService.uploadImage(uploadUrl, filePath);
    }




}

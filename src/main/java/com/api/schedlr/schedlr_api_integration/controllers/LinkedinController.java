package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.DTOs.UploadPost;
import com.api.schedlr.schedlr_api_integration.Service.LinkedinService;
import com.api.schedlr.schedlr_api_integration.Service.PlatformService;
import com.api.schedlr.schedlr_api_integration.util.FileSystemMultipartFile;
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

    @PostMapping("/authCode")
    public String handleLinkedInAuthCode(@RequestBody Map<String, String> requestBody) {
        String code = requestBody.get("code");
        String state = requestBody.get("state");
        int userId = Integer.parseInt(requestBody.get("userId"));
        System.out.println("Thank god it called me..!!");
        return platformService.getAccessToken(userId,code,state);

    }

    @PostMapping("/uploadPost")
    public String uploadPost() throws IOException {
//@RequestBody UploadPost uploadPost
        String filePath = "src/main/java/com/api/schedlr/schedlr_api_integration/images/Groot.jpeg";
        FileSystemMultipartFile f = new FileSystemMultipartFile();
        MultipartFile multipartFile = f.createMultipartFile(filePath);
        return linkedinService.uploadPostLinkedIn(1,multipartFile,"Description");

    }

    @GetMapping("/upload-image")
    public String uploadImage() throws IOException {
        String uploadUrl = "https://www.linkedin.com/dms-uploads/sp/D5622AQGv3PI-R6CH5g/uploaded-image/0?ca=vector_feedshare&cn=uploads&sync=0&v=beta&ut=2JXjezH1o4nbs1";  // Replace with the URL received from step 1
        String filePath = "src/main/java/com/api/schedlr/schedlr_api_integration/images/spdierman.jpg";  // Path to the file on your local system
        String description = "WITH GREAT POWER COMES GREAT RESPONSIBILITY";
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);

        // Create MultipartFile using MockMultipartFile
        MultipartFile image = new MockMultipartFile(file.getName(), file.getName(), filePath, input);
        return linkedinService.uploadPostLinkedIn(1, image, description);
    }






}

package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.Service.LinkedinService;
import com.api.schedlr.schedlr_api_integration.Service.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
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

    @PostMapping("/postupload")
    public String uploadImage(@RequestParam("userId") String userId,
                              @RequestParam("uploadImage") MultipartFile uploadImage,
                              @RequestParam("description") String description) throws IOException {
        log.info("Request"+ description);
        return linkedinService.uploadPostLinkedIn(Integer.parseInt(userId), uploadImage, description);
    }
}
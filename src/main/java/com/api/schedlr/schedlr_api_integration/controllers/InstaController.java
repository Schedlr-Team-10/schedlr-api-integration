package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.Service.InstagramService;
import com.api.schedlr.schedlr_api_integration.util.FileSystemMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/insta")
public class InstaController {

    @Autowired
    private InstagramService instaService;

    @PostMapping("/upload")
    public String uploadImageToInstagram() {
        MultipartFile fileName = new FileSystemMultipartFile(new File("./../Images/Groot.jpeg"));
        String fileType = "jpeg";
        return instaService.uploadImage(fileName, fileType);
    }
    @GetMapping("/health")
    public String health(){
        return "Instagram";
    }
}

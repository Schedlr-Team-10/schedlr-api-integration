package com.api.schedlr.schedlr_api_integration.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadPost {

    private MultipartFile image;
    private String description;
    private int userId;

}



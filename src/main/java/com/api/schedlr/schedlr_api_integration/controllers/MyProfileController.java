package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.DTOs.ChangePasswordRequest;
import com.api.schedlr.schedlr_api_integration.Service.MyProfileService;
import com.api.schedlr.schedlr_api_integration.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/myProfile")
public class MyProfileController {

    @Autowired
    MyProfileService myProfileService;


    @GetMapping("/health")
    public String health(){
        return "Thanks";
    }


    @GetMapping("/userInfo")
    public ResponseEntity<User> getUserInfo(@RequestParam String userId) {
        try {
            User user = myProfileService.getUserById(Integer.parseInt(userId));
            log.info("User"+ user);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return myProfileService.updatePassword(changePasswordRequest.getUserId(), changePasswordRequest.getPassword());
    }


}

package com.api.schedlr.schedlr_api_integration.controllers;

import com.api.schedlr.schedlr_api_integration.DTOs.PInterestAuthData;
import com.api.schedlr.schedlr_api_integration.Service.InstagramService;
import com.api.schedlr.schedlr_api_integration.Service.PInterestService;
import com.api.schedlr.schedlr_api_integration.Service.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pinterest")
public class PInterestController {

    @Autowired
    private PInterestService pInterestService;

    @Autowired
    private PlatformService platformService;

    @GetMapping("/health")
    public String health(){
        return "PInterest";
    }

    @PostMapping("/authCode")
    public String getAccessToken(@RequestBody PInterestAuthData pInterestAuthData){
        log.info("Authcode: "+ pInterestAuthData.getCode()+" : "+pInterestAuthData.getUserId());
        return platformService.getPInterestAccessToken(pInterestAuthData.getUserId(), pInterestAuthData.getCode());
    }


}

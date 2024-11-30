package com.api.schedlr.schedlr_api_integration.Service;

import com.api.schedlr.schedlr_api_integration.Constants.APIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class InstagramService {

    @Autowired
    RestTemplate restTemplate;

    private static final String CLIENT_ID = APIConstants.CLIENT_ID;
    private static final String CLIENT_SECRET = APIConstants.CLIENT_SECRET;

    private final String redirectUri = "http://localhost:3000/";
    private final String state = "DCEeFWf45A53sdfKef424";
    private final String scope = "r_liteprofile%20w_member_social";

    public String callLinkedInAuthorization() {

        String url = UriComponentsBuilder.fromHttpUrl("https://www.linkedin.com/oauth/v2/authorization")
                .queryParam("response_type", "code")
                .queryParam("client_id", CLIENT_ID)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", state)
                .queryParam("scope", scope)
                .build()
                .toUriString();

        
        RestTemplate restTemplate = new RestTemplate();
        String response= response = restTemplate.getForObject(url, String.class);
        System.out.println("Response: " + response);


        return response;
    }

}

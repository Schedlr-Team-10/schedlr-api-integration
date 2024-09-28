package com.api.schedlr.schedlr_api_integration.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class APIConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

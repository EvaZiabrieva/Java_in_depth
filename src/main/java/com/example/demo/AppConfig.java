package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import privatbank.PrivatBankController;
import privatbank.PrivatBankService;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public PrivatBankService privatBankService() {
        return new PrivatBankService(restTemplate(), objectMapper());
    }

    @Bean
    public PrivatBankController privatBankController(){
        return new PrivatBankController(privatBankService());
    }
}
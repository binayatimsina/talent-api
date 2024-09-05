package com.example.talent_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customerOpenAPI() {
        return new OpenAPI()
        .info(new Info()
        .title("Talent Management Platform")
        .version("v1.0")
        .description("API Documentation for Talent Management Platform"));
    }
    
}

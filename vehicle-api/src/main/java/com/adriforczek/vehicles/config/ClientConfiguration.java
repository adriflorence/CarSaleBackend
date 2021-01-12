package com.adriforczek.vehicles.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public WebClient maps(@Value("${maps.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

    @Bean
    public WebClient pricing(@Value("${pricing.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}

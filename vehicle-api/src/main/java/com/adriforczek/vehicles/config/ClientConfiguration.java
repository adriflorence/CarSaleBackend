package com.adriforczek.vehicles.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public WebClient maps() {
        return WebClient.create("maps-service");
    }

    @Bean
    public WebClient pricing() {
        return WebClient.create("pricing-service");
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}

package com.medilabo.solutions.frontend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {

        return RestClient.builder()
                .baseUrl("http://localhost:8881/patient") // URL de base (backendpatient)
                .build();
    }
}

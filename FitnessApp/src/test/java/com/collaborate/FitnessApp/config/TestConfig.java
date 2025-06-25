package com.collaborate.FitnessApp.config;

import com.collaborate.FitnessApp.exceptions.GlobalExceptionHandler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@TestConfiguration
@EnableWebMvc
public class TestConfig {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}

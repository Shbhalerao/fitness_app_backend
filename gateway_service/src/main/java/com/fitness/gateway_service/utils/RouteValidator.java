package com.fitness.gateway_service.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class RouteValidator {

    //private static final Logger log = LoggerFactory.getLogger(RouteValidator.class);

    public static final List<String> openApiEndpoints = List.of(
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/webjars/**",
        "/v2/api-docs",
        "/actuator/**",
        "/api/auth/login"
    );

    public boolean isSecured(String path) {
        log.info("Checking if path is secured: {}", path);
        boolean isSecured = openApiEndpoints.stream().noneMatch(path::matches);
        log.info("Path {} is secured: {}", path, isSecured);
        return isSecured;
    }
}
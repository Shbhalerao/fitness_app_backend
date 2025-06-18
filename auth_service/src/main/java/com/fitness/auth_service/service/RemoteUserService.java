package com.fitness.auth_service.service;

import com.fitness.auth_service.dto.AuthRequest;
import com.fitness.auth_service.dto.AuthResponse;
import com.fitness.auth_service.dto.RefreshTokenRequest;
import com.fitness.auth_service.dto.UserDto;
import com.fitness.auth_service.exception.custom.UserNotFoundException;
import com.fitness.auth_service.utility.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
@Slf4j
public class RemoteUserService {
        private final RestTemplate restTemplate;


    public RemoteUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public UserDto getUserByUsername(String username) {
        String url = "http://localhost:8081/api/fitness-app/users/email/" + username;
        try{
            log.info("Fetching user by username: {}", username);
            UserDto userDto = restTemplate.getForObject(url, UserDto.class);

            if (userDto == null) {
                log.info("User not found for username: {}", username);
                throw new UserNotFoundException("User not found for username: " + username);
            }

            return userDto;

        }catch (HttpClientErrorException.NotFound e) {
            log.error("User not found for username: {}", username, e);
            throw new UserNotFoundException("User not found for username: " + username);
        } catch (Exception e) {
            log.error("Error fetching user by username: {}", username, e);
            throw new RuntimeException("Error fetching user by username: " + username, e);
        }
    }

}

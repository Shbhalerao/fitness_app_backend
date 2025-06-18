package com.fitness.auth_service.service;

import com.fitness.auth_service.dto.AuthRequest;
import com.fitness.auth_service.dto.AuthResponse;
import com.fitness.auth_service.dto.RefreshTokenRequest;
import com.fitness.auth_service.dto.UserDto;
import com.fitness.auth_service.utility.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RemoteUserService remoteUserService;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, TokenService tokenService, RemoteUserService remoteUserService) {
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.remoteUserService = remoteUserService;
    }

    public AuthResponse login(AuthRequest request){
        UserDto userDto = remoteUserService.getUserByUsername(request.getUsername());
        if (userDto == null || !passwordEncoder.matches(request.getPassword(), userDto.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = tokenService.generateToken(userDto);
        String refreshToken = tokenService.generateRefreshToken(userDto);
        Long userId = userDto.getId();
        String role = userDto.getRole();

        return new AuthResponse(accessToken, refreshToken, role, userId);
    }

    public AuthResponse refresh(RefreshTokenRequest refreshTokenRequest){
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = tokenService.extractUsername(refreshToken);

        UserDto userDto = remoteUserService.getUserByUsername(username);

        String accessToken = tokenService.generateToken(userDto);

        Long userId = userDto.getId();
        String role = userDto.getRole();
        return new AuthResponse(accessToken, refreshToken, role, userId);
    }
}

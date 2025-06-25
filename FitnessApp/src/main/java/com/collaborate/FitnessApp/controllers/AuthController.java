package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.responses.AuthUserResponse;
import com.collaborate.FitnessApp.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Tag(name = "Login APIs", description = "Operations related to user login")
public class AuthController {
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<AuthUserResponse> login(@RequestParam String email) {
        AuthUserResponse response = authService.findUserByEmail(email);
        return ResponseEntity.ok(response);
    }
}
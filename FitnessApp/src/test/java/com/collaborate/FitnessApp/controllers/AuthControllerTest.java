package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.config.TestConfig;
import com.collaborate.FitnessApp.domain.dto.responses.AuthUserResponse;
import com.collaborate.FitnessApp.domain.enums.Role;
import com.collaborate.FitnessApp.exceptions.GlobalExceptionHandler;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    private AuthService authService;

    private AuthController authController;

    private AuthUserResponse mockResponse;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        mockResponse = new AuthUserResponse("test@example.com", "password123", Role.ROLE_CLIENT);
        authController = new AuthController(authService);
        mockMvc = MockMvcBuilders
            .standaloneSetup(authController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void login_WithValidEmail_ReturnsUserDetails() throws Exception {
        when(authService.findUserByEmail("test@example.com")).thenReturn(mockResponse);

        mockMvc.perform(get("/api/login")
                .param("email", "test@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    void login_WithInvalidEmail_ReturnsNotFound() throws Exception {
        when(authService.findUserByEmail(anyString()))
                .thenThrow(new ResourceNotFoundException("No user found with email: invalid@example.com"));

        mockMvc.perform(get("/api/login")
                .param("email", "invalid@example.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No user found with email: invalid@example.com"));
    }

    @Test
    void login_WithMissingEmail_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/login")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.config.TestConfig;
import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.exceptions.DuplicateResourceException;
import com.collaborate.FitnessApp.exceptions.GlobalExceptionHandler;
import com.collaborate.FitnessApp.exceptions.ResourceNotFoundException;
import com.collaborate.FitnessApp.services.FitnessCenterService;
import com.collaborate.FitnessApp.utils.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)
class FitnessCenterControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private FitnessCenterService fitnessCenterService;

    private FitnessCenterController fitnessCenterController;

    private FitnessCenterRequest request;
    private FitnessCenterResponse response;

    @BeforeEach
    void setUp() {
        fitnessCenterController = new FitnessCenterController(fitnessCenterService);
        mockMvc = MockMvcBuilders
            .standaloneSetup(fitnessCenterController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
        request = TestDataBuilder.createFitnessCenterRequest();
        response = TestDataBuilder.createFitnessCenterResponse();
    }

    @Test
    void register_WithValidData_ReturnsCreated() throws Exception {
        when(fitnessCenterService.register(any(FitnessCenterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/v1/fitness-centers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.center_name").value("Test Fitness Center"))
                .andExpect(jsonPath("$.emailId").value("test@fitnesscenter.com"));
    }

    @Test
    void register_WithDuplicateEmail_ReturnsBadRequest() throws Exception {
        when(fitnessCenterService.register(any(FitnessCenterRequest.class)))
                .thenThrow(new DuplicateResourceException("Fitness center already exists with email: test@fitnesscenter.com"));

        mockMvc.perform(post("/v1/fitness-centers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Fitness center already exists with email: test@fitnesscenter.com"));
    }

    @Test
    void getById_WithValidId_ReturnsCenter() throws Exception {
        when(fitnessCenterService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/v1/fitness-centers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.center_name").value("Test Fitness Center"));
    }

    @Test
    void getById_WithInvalidId_ReturnsNotFound() throws Exception {
        when(fitnessCenterService.getById(99L))
                .thenThrow(new ResourceNotFoundException("Fitness center not found with id: 99"));

        mockMvc.perform(get("/v1/fitness-centers/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Fitness center not found with id: 99"));
    }

    @Test
    void getByEmail_WithValidEmail_ReturnsCenter() throws Exception {
        when(fitnessCenterService.getByEmailId("test@fitnesscenter.com")).thenReturn(response);

        mockMvc.perform(get("/v1/fitness-centers/email/test@fitnesscenter.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailId").value("test@fitnesscenter.com"));
    }

    @Test
    void getByContactNo_WithValidContact_ReturnsCenter() throws Exception {
        when(fitnessCenterService.getByContactNo("1234567890")).thenReturn(response);

        mockMvc.perform(get("/v1/fitness-centers/contact/1234567890")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactNo").value("1234567890"));
    }

    @Test
    void getFitnessCenters_WithValidStatus_ReturnsPagedResponse() throws Exception {
        Page<FitnessCenterResponse> pagedResponse = new PageImpl<>(List.of(response));
        when(fitnessCenterService.getFitnessCenters(any(), any(Integer.class), any(Integer.class)))
                .thenReturn(pagedResponse);

        mockMvc.perform(get("/v1/fitness-centers")
                .param("statuses", Status.ACTIVE.name())
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"));
    }
}

package com.collaborate.FitnessApp.controllers;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.collaborate.FitnessApp.exceptions.DuplicateResourceException;
import com.collaborate.FitnessApp.exceptions.GlobalExceptionHandler;
import com.collaborate.FitnessApp.services.TrainerService;
import com.collaborate.FitnessApp.utils.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TrainerControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TrainerController trainerController;

    @Mock
    private TrainerService trainerService;

    private TrainerRequest request;
    private TrainerResponse response;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        trainerController = new TrainerController(trainerService);
        mockMvc = MockMvcBuilders
            .standaloneSetup(trainerController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
        request = TestDataBuilder.createTrainerRequest();
        response = TestDataBuilder.createTrainerResponse();
    }

    @Test
    void register_WithValidData_ReturnsCreated() throws Exception {
        when(trainerService.register(any(TrainerRequest.class))).thenReturn(response);

        mockMvc.perform(post("/v1/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(jsonPath("$.emailId").value(response.getEmailId()));
    }

    @Test
    void register_WithDuplicateEmail_ReturnsBadRequest() throws Exception {
        when(trainerService.register(any(TrainerRequest.class)))
                .thenThrow(new DuplicateResourceException("Trainer already exists with email: john.doe@trainer.com"));

        mockMvc.perform(post("/v1/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Trainer already exists with email: john.doe@trainer.com"));
    }

    @Test
    void getById_WithValidId_ReturnsTrainer() throws Exception {
        when(trainerService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/v1/trainers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()));
    }

    @Test
    void getByEmail_WithValidEmail_ReturnsTrainer() throws Exception {
        when(trainerService.getByEmailId("john.doe@trainer.com")).thenReturn(response);

        mockMvc.perform(get("/v1/trainers/email/john.doe@trainer.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailId").value("john.doe@trainer.com"));
    }

    @Test
    void getByContactNo_WithValidContact_ReturnsTrainer() throws Exception {
        when(trainerService.getByContactNo("9876543210")).thenReturn(response);

        mockMvc.perform(get("/v1/trainers/contact/9876543210")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactNo").value("9876543210"));
    }

    @Test
    void update_WithValidData_ReturnsUpdated() throws Exception {
        when(trainerService.update(any(TrainerRequest.class))).thenReturn(response);

        mockMvc.perform(put("/v1/trainers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()));
    }

    @Test
    void delete_WithValidId_ReturnsNoContent() throws Exception {
        doNothing().when(trainerService).delete(anyLong());

        mockMvc.perform(delete("/v1/trainers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByStatus_WithValidStatus_ReturnsList() throws Exception {
        List<TrainerResponse> responseList = List.of(response);
        when(trainerService.getByStatus(Status.ACTIVE.name())).thenReturn(responseList);

        mockMvc.perform(get("/v1/trainers/status/ACTIVE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].status").value(response.getStatus()));
    }

    @Test
    void getPaged_WithValidParams_ReturnsPagedResponse() throws Exception {
        Page<TrainerResponse> pagedResponse = new PageImpl<>(List.of(response));
        when(trainerService.getTrainers(any(), anyInt(), anyInt())).thenReturn(pagedResponse);

        mockMvc.perform(get("/v1/trainers/paged")
                .param("statuses", Status.ACTIVE.name())
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(response.getId()))
                .andExpect(jsonPath("$.content[0].status").value(response.getStatus()));
    }

    @Test
    void getByCenterId_WithValidCenterId_ReturnsPagedResponse() throws Exception {
        Page<TrainerResponse> pagedResponse = new PageImpl<>(List.of(response));
        when(trainerService.getByCenterId(anyLong(), anyInt(), anyInt())).thenReturn(pagedResponse);

        mockMvc.perform(get("/v1/trainers/center/1")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(response.getId()))
                .andExpect(jsonPath("$.content[0].centerId").value(response.getCenterId()));
    }
}

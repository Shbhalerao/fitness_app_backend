package com.collaborate.FitnessApp.utils;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterResponse;
import com.collaborate.FitnessApp.domain.dto.requests.TrainerRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerResponse;
import com.collaborate.FitnessApp.domain.enums.Role;
import com.collaborate.FitnessApp.domain.enums.Status;

public class TestDataBuilder {

    public static FitnessCenterRequest createFitnessCenterRequest() {
        return FitnessCenterRequest.builder()
                .center_name("Test Fitness Center")
                .emailId("test@fitnesscenter.com")
                .contactNo("1234567890")
                .password("password123")
                .role(Role.ROLE_CENTER_ADMIN)
                .build();
    }

    public static FitnessCenterResponse createFitnessCenterResponse() {
        return FitnessCenterResponse.builder()
                .id(1L)
                .center_name("Test Fitness Center")
                .emailId("test@fitnesscenter.com")
                .contactNo("1234567890")
                .role(Role.ROLE_CENTER_ADMIN)
                .status(Status.ACTIVE)
                .build();
    }

    public static TrainerRequest createTrainerRequest() {
        return TrainerRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .emailId("john.doe@trainer.com")
                .contactNo("9876543210")
                .password("trainerPass123")
                .centerId(1L)
                .role(Role.ROLE_TRAINER)
                .build();
    }

    public static TrainerResponse createTrainerResponse() {
        return TrainerResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .emailId("john.doe@trainer.com")
                .contactNo("9876543210")
                .centerId(1L)
                .role(Role.ROLE_TRAINER)
                .status(Status.ACTIVE)
                .build();
    }
}

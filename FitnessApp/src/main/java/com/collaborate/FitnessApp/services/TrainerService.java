package com.collaborate.FitnessApp.services;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrainerService {
    TrainerResponse register(TrainerRequest trainerRequest);
    TrainerResponse getById(Long id);
    TrainerResponse getByEmailId(String emailId);
    TrainerResponse getByContactNo(String contactNo);
    TrainerResponse update(TrainerRequest trainerRequest);
    void delete(Long id);
    List<TrainerResponse> getByStatus(String status);
    Page<TrainerResponse> getTrainers(java.util.List<Status> statuses, int page, int size);
    Page<TrainerResponse> getByCenterId(Long centerId, int page, int size);
}

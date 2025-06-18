package com.collaborate.FitnessApp.services;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FitnessCenterService {
    FitnessCenterResponse register(FitnessCenterRequest fitnessCenterRequest);
    FitnessCenterResponse getById(Long id);
    FitnessCenterResponse getByEmailId(String emailId);
    FitnessCenterResponse getByContactNo(String contactNo);
    FitnessCenterResponse update(FitnessCenterRequest fitnessCenterRequest);
    void delete(Long id);
    List<FitnessCenterResponse> getByStatus(String status);
    Page<FitnessCenterResponse> getFitnessCenters(List<Status> statuses, int page, int size);
}


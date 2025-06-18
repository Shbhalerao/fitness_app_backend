package com.collaborate.FitnessApp.services;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterDetailsResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FitnessCenterDetailsService {
    FitnessCenterDetailsResponse register(FitnessCenterDetailsRequest request);
    FitnessCenterDetailsResponse getById(Long id);
    FitnessCenterDetailsResponse getByCenterId(Long centerId);
    FitnessCenterDetailsResponse update(FitnessCenterDetailsRequest request);
    void delete(Long id);
    List<FitnessCenterDetailsResponse> getByStatus(String status);
    Page<FitnessCenterDetailsResponse> getFitnessCenterDetails(List<Status> statuses, int page, int size);
}

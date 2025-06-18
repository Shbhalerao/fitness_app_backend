package com.collaborate.FitnessApp.services;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerDetailsResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrainerDetailsService {
    public TrainerDetailsResponse create(TrainerDetailsRequest request);

    TrainerDetailsResponse getById(Long id);

    TrainerDetailsResponse update(TrainerDetailsRequest request);

    void delete(Long id);

    Page<TrainerDetailsResponse> getAll(int page, int size);

    Page<TrainerDetailsResponse> getByStatuses(List<Status> statuses, int page, int size);
}

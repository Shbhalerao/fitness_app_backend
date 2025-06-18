package com.collaborate.FitnessApp.services;

import com.collaborate.FitnessApp.domain.dto.requests.ClientDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientDetailsResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientDetailsService {
    ClientDetailsResponse create(ClientDetailsRequest clientDetailsRequest);

    ClientDetailsResponse getByClientId(Long clientId);

    ClientDetailsResponse getById(Long id);

    List<ClientDetailsResponse> getAll();

    Page<ClientDetailsResponse> getByStatus(List<Status> statuses, Pageable pageable);

    Page<ClientDetailsResponse> getClients(List<Status> statuses, int page, int size);

    ClientDetailsResponse update(ClientDetailsRequest clientDetailsRequest);

    boolean updateStatusById(String status, Long id);

    boolean updateStatusByClientId(String status, Long clientId);

    void delete(Long id);
}

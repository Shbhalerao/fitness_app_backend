package com.collaborate.FitnessApp.services;

import com.collaborate.FitnessApp.domain.dto.requests.ClientRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientResponse;
import com.collaborate.FitnessApp.domain.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {
    ClientResponse register(ClientRequest clientRequest);

    ClientResponse getById(Long id);

    ClientResponse getByEmailId(String emailId);

    ClientResponse getByContactNo(String contactNo);

    List<ClientResponse> getByStatus(String status);

    ClientResponse update(ClientRequest clientRequest);

    boolean updateStatusByEmailId(String status, String emailId);

    boolean updateStatusByContactNo(String status, String contactNo);

    void delete(Long id);

    Page<ClientResponse> getClients(List<Status> statuses, int page, int size);
}

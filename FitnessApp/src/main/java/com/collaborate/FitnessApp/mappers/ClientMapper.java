package com.collaborate.FitnessApp.mappers;

import com.collaborate.FitnessApp.domain.dto.requests.ClientRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientResponse;
import com.collaborate.FitnessApp.domain.entities.Client;
import com.collaborate.FitnessApp.domain.entities.Trainer;
import com.collaborate.FitnessApp.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    private final TrainerRepository trainerRepository;

    @Autowired
    public ClientMapper(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }


    public ClientRequest toDto(Client client){
        if(client == null) return null;

        return ClientRequest.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .emailId(client.getEmailId())
                .contactNo(client.getContactNo())
                .dateOfBirth(client.getDateOfBirth())
                .password(client.getPassword())
                .trainerId(client.getTrainerId() != null ? client.getTrainerId().getId() : null)
                .role(client.getRole())
                .build();
    }


    public Client toEntity(ClientRequest clientRequest, Trainer trainer){
        if (clientRequest == null) return null;

        return Client.builder()
                .id(clientRequest.getId() != null ? clientRequest.getId() : null)
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .emailId(clientRequest.getEmailId())
                .contactNo(clientRequest.getContactNo())
                .dateOfBirth(clientRequest.getDateOfBirth())
                .password(clientRequest.getPassword())
                .role(clientRequest.getRole())
                .trainerId(trainer)
                .build();

    }

    public List<Client> toEntityList(List<ClientRequest> clientRequests) {
        if (clientRequests == null || clientRequests.isEmpty()) return Collections.emptyList();

        // Get all distinct trainerIds from the DTO list
        Set<Long> trainerIds = clientRequests.stream()
                .map(ClientRequest::getTrainerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Fetch all relevant trainers in one go
        Map<Long, Trainer> trainerMap = trainerRepository.findAllById(trainerIds)
                .stream()
                .collect(Collectors.toMap(Trainer::getId, Function.identity()));

        // Convert each DTO to Entity using fetched trainers
        return clientRequests.stream()
                .map(dto -> {
                    Trainer trainer = trainerMap.get(dto.getTrainerId());
                    return toEntity(dto, trainer);
                })
                .collect(Collectors.toList());
    }

    public ClientResponse toResponse(Client client) {
        if (client == null) return null;

        ClientResponse response = ClientResponse.builder()
                .id(client.getId())
                .emailId(client.getEmailId())
                .contactNo(client.getContactNo())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .dateOfBirth(client.getDateOfBirth())
                .trainerId(client.getTrainerId() != null ? client.getTrainerId().getId() : null)
                .role(client.getRole())
                .build();

        // Manually set AuditInfo fields
        response.setCreatedBy(client.getCreatedBy());
        response.setCreatedDate(client.getCreatedDate());
        response.setUpdatedBy(client.getUpdatedBy());
        response.setUpdatedDate(client.getUpdatedDate());
        response.setStatus(client.getStatus());

        return response;
    }

    public List<ClientResponse> toResponseList(List<Client> clients) {
        if (clients == null || clients.isEmpty()) return List.of();
        return clients.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}

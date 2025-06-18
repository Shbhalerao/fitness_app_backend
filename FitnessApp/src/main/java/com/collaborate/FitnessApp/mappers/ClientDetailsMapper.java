package com.collaborate.FitnessApp.mappers;

import com.collaborate.FitnessApp.domain.dto.requests.ClientDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.ClientDetailsResponse;
import com.collaborate.FitnessApp.domain.entities.Client;
import com.collaborate.FitnessApp.domain.entities.ClientDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientDetailsMapper {

    public  ClientDetailsRequest toDto(ClientDetails clientDetails){
        if(clientDetails == null) return null;

        return ClientDetailsRequest.builder()
                .id(clientDetails.getId())
                .clientId(clientDetails.getClient().getId())
                .height(clientDetails.getHeight())
                .weight(clientDetails.getWeight())
                .fitnessGoal(clientDetails.getFitnessGoal())
                .addressLine(clientDetails.getAddressLine())
                .city(clientDetails.getCity())
                .state(clientDetails.getState())
                .country(clientDetails.getCountry())
                .pinCode(clientDetails.getPinCode())
                .build();
    }

    public  ClientDetails toEntity(ClientDetailsRequest clientDetailsRequest, Client client){
        if(clientDetailsRequest == null) return null;

        return ClientDetails.builder()
                .id(clientDetailsRequest.getId())
                .client(client)
                .height(clientDetailsRequest.getHeight())
                .weight(clientDetailsRequest.getWeight())
                .fitnessGoal(clientDetailsRequest.getFitnessGoal())
                .addressLine(clientDetailsRequest.getAddressLine())
                .city(clientDetailsRequest.getCity())
                .state(clientDetailsRequest.getState())
                .country(clientDetailsRequest.getCountry())
                .pinCode(clientDetailsRequest.getPinCode())
                .build();
    }

    public ClientDetailsResponse toResponse(ClientDetails clientDetails) {
        if (clientDetails == null) {
            return null;
        }

        return ClientDetailsResponse.builder()
                .id(clientDetails.getId())
                .clientId(clientDetails.getClient() != null ? clientDetails.getClient().getId() : null)
                .height(clientDetails.getHeight())
                .weight(clientDetails.getWeight())
                .fitnessGoal(clientDetails.getFitnessGoal())
                .addressLine(clientDetails.getAddressLine())
                .city(clientDetails.getCity())
                .state(clientDetails.getState())
                .country(clientDetails.getCountry())
                .pinCode(clientDetails.getPinCode())
                // Audit Fields
                .createdBy(clientDetails.getCreatedBy())
                .createdDate(clientDetails.getCreatedDate())
                .updatedBy(clientDetails.getUpdatedBy())
                .updatedDate(clientDetails.getUpdatedDate())
                .status(clientDetails.getStatus())
                .build();
    }

    public List<ClientDetailsResponse> toResponseList(List<ClientDetails> clientDetailsList) {
        if (clientDetailsList == null || clientDetailsList.isEmpty()) {
            return List.of();
        }

        return clientDetailsList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}

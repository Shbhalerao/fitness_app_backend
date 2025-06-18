package com.collaborate.FitnessApp.mappers;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerDetailsResponse;
import com.collaborate.FitnessApp.domain.entities.Trainer;
import com.collaborate.FitnessApp.domain.entities.TrainerDetails;
import java.util.List;
import java.util.stream.Collectors;

public class TrainerDetailsMapper {

    public static TrainerDetailsRequest toDto(TrainerDetails trainerDetails){
        if(trainerDetails == null) return null;

        return TrainerDetailsRequest.builder()
                .id(trainerDetails.getId())
                .trainerId(trainerDetails.getTrainerId().getId())
                .workingSince(trainerDetails.getWorkingSince())
                .addressLine(trainerDetails.getAddressLine())
                .city(trainerDetails.getCity())
                .state(trainerDetails.getState())
                .country(trainerDetails.getCountry())
                .pinCode(trainerDetails.getPinCode())
                .build();
    }

    public static TrainerDetails toEntity(TrainerDetailsRequest trainerDetailsRequest, Trainer trainer){
        if(trainerDetailsRequest == null) return null;

        return TrainerDetails.builder()
                .id(trainerDetailsRequest.getId())
                .trainerId(trainer)
                .workingSince(trainerDetailsRequest.getWorkingSince())
                .addressLine(trainerDetailsRequest.getAddressLine())
                .city(trainerDetailsRequest.getCity())
                .state(trainerDetailsRequest.getState())
                .country(trainerDetailsRequest.getCountry())
                .pinCode(trainerDetailsRequest.getPinCode())
                .build();
    }

    public static TrainerDetailsResponse toResponse(TrainerDetails trainerDetails) {
        if (trainerDetails == null) return null;
        return TrainerDetailsResponse.builder()
                .id(trainerDetails.getId())
                .trainerId(trainerDetails.getTrainerId() != null ? trainerDetails.getTrainerId().getId() : null)
                .workingSince(trainerDetails.getWorkingSince())
                .addressLine(trainerDetails.getAddressLine())
                .city(trainerDetails.getCity())
                .state(trainerDetails.getState())
                .country(trainerDetails.getCountry())
                .pinCode(trainerDetails.getPinCode())
                .createdBy(trainerDetails.getCreatedBy())
                .createdDate(trainerDetails.getCreatedDate())
                .updatedBy(trainerDetails.getUpdatedBy())
                .updatedDate(trainerDetails.getUpdatedDate())
                .status(trainerDetails.getStatus() != null ? trainerDetails.getStatus() : null)
                .build();
    }

    public static List<TrainerDetailsResponse> toResponseList(List<TrainerDetails> detailsList) {
        return detailsList.stream().map(TrainerDetailsMapper::toResponse).collect(Collectors.toList());
    }
}

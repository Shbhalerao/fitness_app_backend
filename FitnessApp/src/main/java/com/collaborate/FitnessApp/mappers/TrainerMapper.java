package com.collaborate.FitnessApp.mappers;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerRequest;
import com.collaborate.FitnessApp.domain.dto.responses.TrainerResponse;
import com.collaborate.FitnessApp.domain.entities.Trainer;

import java.util.List;
import java.util.stream.Collectors;

public class TrainerMapper {

    public static TrainerRequest toDto(Trainer trainer){
        if(trainer == null) return null;

        return TrainerRequest.builder()
                .id(trainer.getId())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .emailId(trainer.getEmailId())
                .centerId(trainer.getCenter().getId())
                .password(trainer.getPassword())
                .contactNo(trainer.getContactNo())
                .role(trainer.getRole())
                .build();
    }

    public static Trainer toEntity(TrainerRequest trainerRequest){
        if(trainerRequest == null) return null;

        return Trainer.builder()
                .id(trainerRequest.getId())
                .firstName(trainerRequest.getFirstName())
                .lastName(trainerRequest.getLastName())
                .emailId(trainerRequest.getEmailId())
                .password(trainerRequest.getPassword())
                .contactNo(trainerRequest.getContactNo())
                .role(trainerRequest.getRole())
                .build();
    }

    public static TrainerResponse toResponse(Trainer trainer) {
        if (trainer == null) return null;
        return TrainerResponse.builder()
                .id(trainer.getId())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .emailId(trainer.getEmailId())
                .contactNo(trainer.getContactNo())
                .centerId(trainer.getCenter() != null ? trainer.getCenter().getId() : null)
                .role(trainer.getRole())
                .createdBy(trainer.getCreatedBy())
                .createdDate(trainer.getCreatedDate())
                .updatedBy(trainer.getUpdatedBy())
                .updatedDate(trainer.getUpdatedDate())
                .status(trainer.getStatus() != null ? trainer.getStatus() : null)
                .build();
    }

    public static List<TrainerResponse> toDtoList(List<Trainer> trainers) {
        return trainers.stream().map(TrainerMapper::toResponse).collect(Collectors.toList());
    }
}

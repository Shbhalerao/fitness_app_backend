package com.collaborate.FitnessApp.mappers;

import com.collaborate.FitnessApp.domain.dto.requests.TrainerCertificationsRequest;
import com.collaborate.FitnessApp.domain.entities.Trainer;
import com.collaborate.FitnessApp.domain.entities.TrainerCertifications;

public class TrainerCertificationsMapper {

    public static TrainerCertificationsRequest toDto(TrainerCertifications trainerCertifications){
        if(trainerCertifications == null) return null;

        return TrainerCertificationsRequest.builder()
                .id(trainerCertifications.getId())
                .trainerId(trainerCertifications.getTrainerId().getId())
                .certificationName(trainerCertifications.getCertificationName())
                .certificationDate(trainerCertifications.getCertificationDate())
                .build();

    }

    public static TrainerCertifications toEntity(TrainerCertificationsRequest trainerCertificationsRequest, Trainer trainer){
        if(trainerCertificationsRequest == null) return null;

        return TrainerCertifications.builder()
                .id(trainerCertificationsRequest.getId())
                .trainerId(trainer)
                .certificationDate(trainerCertificationsRequest.getCertificationDate())
                .certificationName(trainerCertificationsRequest.getCertificationName())
                .build();
    }
}

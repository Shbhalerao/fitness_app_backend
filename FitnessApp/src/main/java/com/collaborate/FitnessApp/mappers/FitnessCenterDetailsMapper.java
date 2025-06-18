package com.collaborate.FitnessApp.mappers;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterDetailsRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterDetailsResponse;
import com.collaborate.FitnessApp.domain.entities.FitnessCenter;
import com.collaborate.FitnessApp.domain.entities.FitnessCenterDetails;
import com.collaborate.FitnessApp.domain.enums.Status;

public class FitnessCenterDetailsMapper {

    public static FitnessCenterDetailsRequest toDto(FitnessCenterDetails fitnessCenterDetails){
            if(fitnessCenterDetails == null) return null;

            return FitnessCenterDetailsRequest.builder()
                    .id(fitnessCenterDetails.getId())
                    .centerId(fitnessCenterDetails.getCenter().getId())
                    .addressLine(fitnessCenterDetails.getAddressLine())
                    .city(fitnessCenterDetails.getCity())
                    .state(fitnessCenterDetails.getState())
                    .country(fitnessCenterDetails.getCountry())
                    .pinCode(fitnessCenterDetails.getPinCode())
                    .build();
    }

    public static FitnessCenterDetails toEntity(FitnessCenterDetailsRequest dto, FitnessCenter fitnessCenter){
        if (dto == null) return null;

        return FitnessCenterDetails.builder()
                .id(dto.getId())
                .center(fitnessCenter)
                .addressLine(dto.getAddressLine())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .pinCode(dto.getPinCode())
                .build();
    }

    public static FitnessCenterDetailsResponse toResponseDto(FitnessCenterDetails fitnessCenterDetails) {
        if (fitnessCenterDetails == null) return null;
        return FitnessCenterDetailsResponse.builder()
                .id(fitnessCenterDetails.getId())
                .centerId(fitnessCenterDetails.getCenter() != null ? fitnessCenterDetails.getCenter().getId() : null)
                .addressLine(fitnessCenterDetails.getAddressLine())
                .city(fitnessCenterDetails.getCity())
                .state(fitnessCenterDetails.getState())
                .country(fitnessCenterDetails.getCountry())
                .pinCode(fitnessCenterDetails.getPinCode())
                .createdBy(fitnessCenterDetails.getCreatedBy())
                .createdDate(fitnessCenterDetails.getCreatedDate())
                .updatedBy(fitnessCenterDetails.getUpdatedBy())
                .updatedDate(fitnessCenterDetails.getUpdatedDate())
                .status(fitnessCenterDetails.getStatus())
                .build();
    }
}

package com.collaborate.FitnessApp.mappers;

import com.collaborate.FitnessApp.domain.dto.requests.FitnessCenterRequest;
import com.collaborate.FitnessApp.domain.dto.responses.FitnessCenterResponse;
import com.collaborate.FitnessApp.domain.entities.FitnessCenter;

public class FitnessCenterMapper {

    public static FitnessCenterRequest toDto(FitnessCenter fitnessCenter){
        if(fitnessCenter == null) return null;

        return FitnessCenterRequest.builder()
                .id(fitnessCenter.getId())
                .center_name(fitnessCenter.getCenter_name())
                .emailId(fitnessCenter.getEmailId())
                .contactNo(fitnessCenter.getContactNo())
                .password(fitnessCenter.getPassword())
                .role(fitnessCenter.getRole())
                .build();
    }

    public static FitnessCenter toEntity(FitnessCenterRequest fitnessCenterRequest){
        if(fitnessCenterRequest == null) return null;

        return FitnessCenter.builder()
                .id(fitnessCenterRequest.getId())
                .center_name(fitnessCenterRequest.getCenter_name())
                .emailId(fitnessCenterRequest.getEmailId())
                .password(fitnessCenterRequest.getPassword())
                .contactNo(fitnessCenterRequest.getContactNo())
                .role(fitnessCenterRequest.getRole())
                .build();
    }

    public static FitnessCenterResponse toResponseDto(FitnessCenter fitnessCenter){
        if(fitnessCenter == null) return null;
        return FitnessCenterResponse.builder()
                .id(fitnessCenter.getId())
                .center_name(fitnessCenter.getCenter_name())
                .emailId(fitnessCenter.getEmailId())
                .contactNo(fitnessCenter.getContactNo())
                .role(fitnessCenter.getRole())
                .createdBy(fitnessCenter.getCreatedBy())
                .createdDate(fitnessCenter.getCreatedDate())
                .updatedBy(fitnessCenter.getUpdatedBy())
                .updatedDate(fitnessCenter.getUpdatedDate())
                .status(fitnessCenter.getStatus())
                .build();
    }
}

package com.collaborate.FitnessApp.domain.dto.requests;
import lombok.*;

@Builder
@Getter
@Setter
public class FitnessCenterDetailsRequest {

    private Long id;
    private Long centerId;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private Long pinCode;
}

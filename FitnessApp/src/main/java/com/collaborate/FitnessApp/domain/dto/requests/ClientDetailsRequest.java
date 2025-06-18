package com.collaborate.FitnessApp.domain.dto.requests;


import lombok.*;


@Builder
@Getter
@Setter
public class ClientDetailsRequest {

    private Long id;
    private Long clientId;
    private String height;
    private Double weight;
    private String fitnessGoal;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private Long pinCode;

}

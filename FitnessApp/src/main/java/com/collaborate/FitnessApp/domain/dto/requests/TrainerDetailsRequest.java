package com.collaborate.FitnessApp.domain.dto.requests;

import lombok.*;

import java.util.Date;


@Builder
@Getter
@Setter
public class TrainerDetailsRequest {

    private Long id;
    private Long trainerId;
    private Date workingSince;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private Long pinCode;

}

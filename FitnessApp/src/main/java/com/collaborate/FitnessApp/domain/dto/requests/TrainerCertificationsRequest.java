package com.collaborate.FitnessApp.domain.dto.requests;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
public class TrainerCertificationsRequest {

    private Long id;
    private Long trainerId;
    private String certificationName;
    private Date certificationDate;
}

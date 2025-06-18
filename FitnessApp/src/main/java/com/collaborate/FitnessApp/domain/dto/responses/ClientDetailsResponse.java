package com.collaborate.FitnessApp.domain.dto.responses;

import com.collaborate.FitnessApp.domain.base.AuditInfo;
import com.collaborate.FitnessApp.domain.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClientDetailsResponse {

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

    //Audit Fields
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private Status status;
}

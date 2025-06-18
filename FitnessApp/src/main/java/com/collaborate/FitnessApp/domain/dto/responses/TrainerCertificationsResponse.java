package com.collaborate.FitnessApp.domain.dto.responses;

import com.collaborate.FitnessApp.domain.base.AuditInfo;
import com.collaborate.FitnessApp.domain.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TrainerCertificationsResponse {
    private Long id;
    private Long trainerId;
    private String certificationName;
    private Date certificationDate;

    //Audit Fields
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private Status status;
}

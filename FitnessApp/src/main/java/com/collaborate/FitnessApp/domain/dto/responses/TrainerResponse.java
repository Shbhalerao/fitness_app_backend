package com.collaborate.FitnessApp.domain.dto.responses;

import com.collaborate.FitnessApp.domain.base.AuditInfo;
import com.collaborate.FitnessApp.domain.enums.Role;
import com.collaborate.FitnessApp.domain.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TrainerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String contactNo;
    private Long centerId;
    private Role role;

    //Audit Fields
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private Status status;
}

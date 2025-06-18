package com.collaborate.FitnessApp.domain.dto.responses;

import com.collaborate.FitnessApp.domain.base.AuditInfo;
import com.collaborate.FitnessApp.domain.enums.Role;
import com.collaborate.FitnessApp.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClientResponse {
    private Long id;

    private String emailId;

    private String contactNo;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private Long trainerId;

    private Role role;

    //Audit Fields
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private Status status;
}

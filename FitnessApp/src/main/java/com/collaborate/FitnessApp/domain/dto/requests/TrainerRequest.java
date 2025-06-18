package com.collaborate.FitnessApp.domain.dto.requests;

import com.collaborate.FitnessApp.domain.enums.Role;
import lombok.*;


@Getter
@Setter
@Builder
public class TrainerRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String contactNo;
    private Long centerId;
    private Role role;
}

package com.collaborate.FitnessApp.domain.dto.requests;

import com.collaborate.FitnessApp.domain.enums.Role;
import lombok.*;

@Builder
@Getter
@Setter
public class FitnessCenterRequest {

    private Long id;
    private String center_name;
    private String emailId;
    private String contactNo;
    private String password;
    private Role role;
}

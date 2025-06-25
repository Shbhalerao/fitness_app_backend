package com.collaborate.FitnessApp.domain.dto.responses;

import com.collaborate.FitnessApp.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserResponse {
    private String email;
    private String password;
    private Role role;
}


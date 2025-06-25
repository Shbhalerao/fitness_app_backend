package com.collaborate.FitnessApp.services;

import com.collaborate.FitnessApp.domain.dto.responses.AuthUserResponse;

public interface AuthService {
    AuthUserResponse findUserByEmail(String email);
}


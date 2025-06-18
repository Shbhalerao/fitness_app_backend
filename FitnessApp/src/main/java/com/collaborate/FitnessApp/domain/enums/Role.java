package com.collaborate.FitnessApp.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@Schema(description = "Role of the entity")
public enum Role {
    ROLE_CLIENT,
    ROLE_TRAINER,
    ROLE_CENTER_ADMIN
}

package com.collaborate.FitnessApp.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(description = "Status of the entity")
public enum Status {
    ACTIVE,
    INACTIVE,
    INCOMPLETE,
    PENDING,
    BLOCKED;


    public static boolean isExactStatus(String status) {
        return Arrays.stream(Status.values())
                .anyMatch(s -> s.name().equals(status));
    }
}

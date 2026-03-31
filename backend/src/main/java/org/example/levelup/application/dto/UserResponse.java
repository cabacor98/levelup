package org.example.levelup.application.dto;

import java.time.OffsetDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String documentType,
        String documentNumber,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}

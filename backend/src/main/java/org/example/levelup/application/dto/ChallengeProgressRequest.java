package org.example.levelup.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ChallengeProgressRequest(
        @NotNull(message = "El progreso es obligatorio")
        @Min(value = 0, message = "El progreso minimo es 0")
        @Max(value = 100, message = "El progreso maximo es 100")
        Integer progressPercentage
) {
}

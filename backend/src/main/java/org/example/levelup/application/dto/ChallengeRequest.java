package org.example.levelup.application.dto;

import java.time.LocalDate;

import org.example.levelup.domain.model.ChallengeDifficulty;
import org.example.levelup.domain.model.ChallengeType;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChallengeRequest(
        @NotNull(message = "El id del usuario es obligatorio")
        Long userId,

        @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 120, message = "El titulo no puede superar 120 caracteres")
        String title,

        @NotBlank(message = "La descripcion es obligatoria")
        @Size(max = 400, message = "La descripcion no puede superar 400 caracteres")
        String description,

        @NotNull(message = "El tipo de reto es obligatorio")
        ChallengeType type,

        @NotNull(message = "La dificultad es obligatoria")
        ChallengeDifficulty difficulty,

        @NotNull(message = "La fecha objetivo es obligatoria")
        @FutureOrPresent(message = "La fecha objetivo no puede estar en el pasado")
        LocalDate targetDate
) {
}

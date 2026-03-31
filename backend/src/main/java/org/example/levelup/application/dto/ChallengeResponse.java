package org.example.levelup.application.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.example.levelup.domain.model.ChallengeDifficulty;
import org.example.levelup.domain.model.ChallengeStatus;
import org.example.levelup.domain.model.ChallengeType;

public record ChallengeResponse(
        Long id,
        Long userId,
        String userName,
        String title,
        String description,
        ChallengeType type,
        ChallengeDifficulty difficulty,
        Integer progressPercentage,
        LocalDate targetDate,
        ChallengeStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}

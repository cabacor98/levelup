package org.example.levelup.application.mapper;

import org.example.levelup.application.dto.ChallengeRequest;
import org.example.levelup.application.dto.ChallengeResponse;
import org.example.levelup.domain.model.ChallengeEntity;
import org.example.levelup.domain.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ChallengeMapper {
    public ChallengeEntity toEntity(ChallengeRequest request, UserEntity user) {
        ChallengeEntity challenge = new ChallengeEntity();
        challenge.setUser(user);
        challenge.setTitle(request.title());
        challenge.setDescription(request.description());
        challenge.setType(request.type());
        challenge.setDifficulty(request.difficulty());
        challenge.setTargetDate(request.targetDate());
        return challenge;
    }

    public void updateEntity(ChallengeEntity challenge, ChallengeRequest request, UserEntity user) {
        challenge.setUser(user);
        challenge.setTitle(request.title());
        challenge.setDescription(request.description());
        challenge.setType(request.type());
        challenge.setDifficulty(request.difficulty());
        challenge.setTargetDate(request.targetDate());
    }

    public ChallengeResponse toResponse(ChallengeEntity challenge) {
        return new ChallengeResponse(
                challenge.getId(),
                challenge.getUser().getId(),
                challenge.getUser().getFullName(),
                challenge.getTitle(),
                challenge.getDescription(),
                challenge.getType(),
                challenge.getDifficulty(),
                challenge.getProgressPercentage(),
                challenge.getTargetDate(),
                challenge.getStatus(),
                challenge.getCreatedAt(),
                challenge.getUpdatedAt()
        );
    }
}

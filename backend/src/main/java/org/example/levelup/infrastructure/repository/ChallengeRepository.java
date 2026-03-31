package org.example.levelup.infrastructure.repository;

import java.util.List;

import org.example.levelup.domain.model.ChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<ChallengeEntity, Long> {
    List<ChallengeEntity> findByUserId(Long userId);
}

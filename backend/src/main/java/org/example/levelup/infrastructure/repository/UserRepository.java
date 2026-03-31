package org.example.levelup.infrastructure.repository;

import java.util.Optional;

import org.example.levelup.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}

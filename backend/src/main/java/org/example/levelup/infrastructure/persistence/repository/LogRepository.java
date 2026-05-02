package org.example.levelup.infrastructure.persistence.repository;

import org.example.levelup.infrastructure.persistence.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
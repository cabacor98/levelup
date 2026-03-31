package org.example.levelup.application.dto;

public record AuthRequest(
        String email,
        String password) {
}
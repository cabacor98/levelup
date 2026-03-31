package org.example.levelup.application.mapper;

import org.example.levelup.application.dto.UserRequest;
import org.example.levelup.application.dto.UserResponse;
import org.example.levelup.domain.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(UserRequest request) {
        UserEntity user = new UserEntity();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setDocumentType(request.documentType());
        user.setDocumentNumber(request.documentNumber());
        return user;
    }

    public void updateEntity(UserEntity user, UserRequest request) {
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setDocumentType(request.documentType());
        user.setDocumentNumber(request.documentNumber());
    }

    public UserResponse toResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getDocumentType(),
                user.getDocumentNumber(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

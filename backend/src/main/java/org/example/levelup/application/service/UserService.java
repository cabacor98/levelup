package org.example.levelup.application.service;

import java.util.List;

import org.example.levelup.application.dto.UserRequest;
import org.example.levelup.application.dto.UserResponse;
import org.example.levelup.application.mapper.UserMapper;
import org.example.levelup.domain.exception.BusinessException;
import org.example.levelup.domain.exception.ResourceNotFoundException;
import org.example.levelup.domain.model.UserEntity;
import org.example.levelup.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse create(UserRequest request) {
        validateEmailAvailability(request.email(), null);
        return userMapper.toResponse(userRepository.save(userMapper.toEntity(request)));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return userMapper.toResponse(getUserEntity(id));
    }

    public UserResponse update(Long id, UserRequest request) {
        UserEntity user = getUserEntity(id);
        validateEmailAvailability(request.email(), id);
        userMapper.updateEntity(user, request);
        return userMapper.toResponse(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.delete(getUserEntity(id));
    }

    @Transactional(readOnly = true)
    public UserEntity getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con id " + id));
    }

    private void validateEmailAvailability(String email, Long currentUserId) {
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            boolean belongsToOtherUser = currentUserId == null || !existingUser.getId().equals(currentUserId);
            if (belongsToOtherUser) {
                throw new BusinessException("El correo " + email + " ya se encuentra registrado");
            }
        });
    }
}

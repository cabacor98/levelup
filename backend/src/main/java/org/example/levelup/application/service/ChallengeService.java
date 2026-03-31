package org.example.levelup.application.service;

import java.util.List;

import org.example.levelup.application.dto.ChallengeProgressRequest;
import org.example.levelup.application.dto.ChallengeRequest;
import org.example.levelup.application.dto.ChallengeResponse;
import org.example.levelup.application.mapper.ChallengeMapper;
import org.example.levelup.domain.exception.ResourceNotFoundException;
import org.example.levelup.domain.model.ChallengeEntity;
import org.example.levelup.domain.model.ChallengeStatus;
import org.example.levelup.domain.model.UserEntity;
import org.example.levelup.infrastructure.repository.ChallengeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;
    private final UserService userService;

    public ChallengeService(
            ChallengeRepository challengeRepository,
            ChallengeMapper challengeMapper,
            UserService userService
    ) {
        this.challengeRepository = challengeRepository;
        this.challengeMapper = challengeMapper;
        this.userService = userService;
    }

    public ChallengeResponse create(ChallengeRequest request) {
        UserEntity user = userService.getUserEntity(request.userId());
        ChallengeEntity challenge = challengeMapper.toEntity(request, user);
        return challengeMapper.toResponse(challengeRepository.save(challenge));
    }

    @Transactional(readOnly = true)
    public List<ChallengeResponse> findAll() {
        return challengeRepository.findAll()
                .stream()
                .map(challengeMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ChallengeResponse findById(Long id) {
        return challengeMapper.toResponse(getChallengeEntity(id));
    }

    @Transactional(readOnly = true)
    public List<ChallengeResponse> findByUserId(Long userId) {
        return challengeRepository.findByUserId(userId)
                .stream()
                .map(challengeMapper::toResponse)
                .toList();
    }

    public ChallengeResponse update(Long id, ChallengeRequest request) {
        ChallengeEntity challenge = getChallengeEntity(id);
        UserEntity user = userService.getUserEntity(request.userId());
        challengeMapper.updateEntity(challenge, request, user);
        return challengeMapper.toResponse(challengeRepository.save(challenge));
    }

    public ChallengeResponse updateProgress(Long id, ChallengeProgressRequest request) {
        ChallengeEntity challenge = getChallengeEntity(id);
        challenge.setProgressPercentage(request.progressPercentage());
        challenge.setStatus(resolveStatus(request.progressPercentage()));
        return challengeMapper.toResponse(challengeRepository.save(challenge));
    }

    public void delete(Long id) {
        challengeRepository.delete(getChallengeEntity(id));
    }

    @Transactional(readOnly = true)
    public ChallengeEntity getChallengeEntity(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un reto con id " + id));
    }

    private ChallengeStatus resolveStatus(Integer progressPercentage) {
        if (progressPercentage == 0) {
            return ChallengeStatus.CREATED;
        }
        if (progressPercentage == 100) {
            return ChallengeStatus.COMPLETED;
        }
        return ChallengeStatus.IN_PROGRESS;
    }
}

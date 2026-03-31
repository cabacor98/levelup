package org.example.levelup.infrastructure.controller;

import java.net.URI;
import java.util.List;

import org.example.levelup.application.dto.ChallengeProgressRequest;
import org.example.levelup.application.dto.ChallengeRequest;
import org.example.levelup.application.dto.ChallengeResponse;
import org.example.levelup.application.service.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/challenges")
@Validated
public class ChallengeController {
    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    public ResponseEntity<ChallengeResponse> create(@RequestBody @Valid ChallengeRequest request) {
        ChallengeResponse response = challengeService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public List<ChallengeResponse> findAll(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            return challengeService.findByUserId(userId);
        }
        return challengeService.findAll();
    }

    @GetMapping("/{id}")
    public ChallengeResponse findById(@PathVariable Long id) {
        return challengeService.findById(id);
    }

    @PutMapping("/{id}")
    public ChallengeResponse update(@PathVariable Long id, @RequestBody @Valid ChallengeRequest request) {
        return challengeService.update(id, request);
    }

    @PatchMapping("/{id}/progress")
    public ChallengeResponse updateProgress(@PathVariable Long id, @RequestBody @Valid ChallengeProgressRequest request) {
        return challengeService.updateProgress(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        challengeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package org.example.levelup.infrastructure.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.example.levelup.application.dto.AuthRequest;
import org.example.levelup.application.dto.RegisterRequest;
import org.example.levelup.application.dto.MetadataRequest;
import org.example.levelup.application.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.client-id}")
    private String clientId;

    @Value("${auth0.client-secret}")
    private String clientSecret;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 🔐 LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.email(), request.password());
        return ResponseEntity.ok(Map.of("access_token", token));
    }

    // 📝 REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        String managementToken = getManagementToken();

        try {
            authService.register(request, managementToken);
            return ResponseEntity.ok(Map.of("message", "Usuario creado"));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 📄 METADATA
    @PostMapping("/metadata/{userId}")
    public ResponseEntity<?> metadata(
            @PathVariable String userId,
            @RequestBody MetadataRequest request) {
        
        String managementToken = getManagementToken();
        

        authService.saveMetadata(userId, request, managementToken);

        return ResponseEntity.ok("Metadata guardada");
    }

    // 🔑 TOKEN DE MANAGEMENT API
    private String getManagementToken() {

        String url = "https://" + domain + "/oauth/token";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", "https://" + domain + "/api/v2/");
        body.put("grant_type", "client_credentials");
        
        ResponseEntity<Map> response = restTemplate.postForEntity(url, body, Map.class);

        System.out.println("STATUS: " + response.getStatusCode());
        System.out.println("BODY: " + response.getBody());

        if (response.getBody() == null) {
            throw new RuntimeException("Auth0 no devolvió token");
        }

        return (String) response.getBody().get("access_token");
    }
}
package org.example.levelup.application.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.example.levelup.application.dto.RegisterRequest;
import org.example.levelup.application.dto.MetadataRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.client-id}")
    private String clientId;

    @Value("${auth0.client-secret}")
    private String clientSecret;

    @Value("${auth0.audience}")
    private String audience;

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔐 LOGIN
    public String login(String email, String password) {
        String url = "https://" + domain + "/oauth/token";

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "password");
        body.put("username", email);
        body.put("password", password);
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", audience);
        body.put("scope", "openid profile email");

        ResponseEntity<Map> response = restTemplate.postForEntity(url, body, Map.class);

        return (String) response.getBody().get("access_token");
    }

    // 📝 REGISTER
    public void register(RegisterRequest request, String managementToken) {

        String url = "https://" + domain + "/api/v2/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(managementToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("email", request.email());
        body.put("password", request.password());
        body.put("connection", "Username-Password-Authentication");
        body.put("name", request.name());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            System.out.println("Auth0 response: " + response.getBody());

        } catch (HttpClientErrorException.Conflict e) {
            throw new RuntimeException("El usuario ya existe en Auth0");

        } catch (HttpClientErrorException e) {
            System.out.println("Auth0 error body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Error en Auth0: " + e.getMessage());

        } catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage(), e);
        }
    }

    // 📄 GUARDAR METADATA
    public void saveMetadata(String userId, MetadataRequest request, String managementToken) {

        String url = "https://" + domain + "/api/v2/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(managementToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("tipo_documento", request.tipoDocumento());
        metadata.put("numero_documento", request.numeroDocumento());

        Map<String, Object> body = new HashMap<>();
        body.put("user_metadata", metadata);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
    }
}
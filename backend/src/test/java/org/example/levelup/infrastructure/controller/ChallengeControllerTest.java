package org.example.levelup.infrastructure.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ChallengeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAndUpdateChallengeProgress() throws Exception {
        String userPayload = """
                {
                  "fullName": "Cesar Barrientos",
                  "email": "cesar@levelup.com",
                  "documentType": "CC",
                  "documentNumber": "1122334455"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userPayload))
                .andExpect(status().isCreated());

        String challengePayload = """
                {
                  "userId": 1,
                  "title": "Hablar en publico durante 5 minutos",
                  "description": "Practicar semanalmente para perder el miedo",
                  "type": "SOCIAL",
                  "difficulty": "MEDIUM",
                  "targetDate": "2030-12-31"
                }
                """;

        mockMvc.perform(post("/api/challenges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(challengePayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"));

        mockMvc.perform(patch("/api/challenges/1/progress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "progressPercentage": 60
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.progressPercentage").value(60))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        mockMvc.perform(get("/api/challenges?userId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Hablar en publico durante 5 minutos"));
    }
}

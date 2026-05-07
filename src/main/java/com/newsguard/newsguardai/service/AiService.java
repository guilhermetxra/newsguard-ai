package com.newsguard.newsguardai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public AiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .build();
    }

    public String analyzeNews(String content) {

        String prompt = """
                Analise a notícia abaixo e gere:

                1. Resumo curto
                2. 3 títulos
                3. 1 legenda para Instagram
                4. 3 comentários
                5. 1 CTA

                Notícia:
                """ + content;

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4.1-mini",
                "messages", new Object[]{
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                }
        );

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
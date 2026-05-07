package com.newsguard.newsguardai.controller;

import com.newsguard.newsguardai.dto.NewsRequest;
import com.newsguard.newsguardai.service.AiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final AiService aiService;

    public NewsController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/analyze")
    public String analyze(@RequestBody NewsRequest request) {
        return aiService.analyzeNews(request.getContent());
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

import com.xhht.dto.ChatRequest;
import com.xhht.dto.ChatResponse;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author lehuy
 */
@Service
public class DeepSeekAIService {
    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "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"; 

    public String getAIResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();

        // patient va assitant
        ChatRequest request = new ChatRequest();
        request.setMessages(List.of(
            new ChatRequest.Message("user", userMessage)
        ));

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

        // gui request toi OpenRouter
        ResponseEntity<ChatResponse> response = restTemplate.exchange(
            OPENROUTER_API_URL,
            HttpMethod.POST,
            entity,
            ChatResponse.class
        );

        // response cua AI
        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}

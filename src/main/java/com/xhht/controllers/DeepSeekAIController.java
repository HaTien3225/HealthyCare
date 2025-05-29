/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.dto.ResponseDTO;
import com.xhht.services.DeepSeekAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lehuy
 */
@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class DeepSeekAIController {

    @Autowired
    private DeepSeekAIService AIService;

    @PostMapping("/assitant")
    public ResponseEntity<?> chatWithAI(@RequestBody String userMessage) {
        ResponseDTO r = new ResponseDTO();
        r.setResponse(this.AIService.getAIResponse(userMessage));    
        return ResponseEntity.ok(r);
    }
}

package com.quizplatform.controller;

import com.quizplatform.dto.QuestionRequest;
import com.quizplatform.entity.Quiz;
import com.quizplatform.service.GenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/genai")
@CrossOrigin(origins = "*")
public class GenAIController {
    
    @Autowired
    private GenAIService genAIService;

    @PostMapping("/generate-questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuestionRequest>> generateQuestions(@RequestBody Map<String, Object> request) {
        String topic = (String) request.get("topic");
        Quiz.Difficulty difficulty = Quiz.Difficulty.valueOf((String) request.get("difficulty"));
        Integer count = (Integer) request.get("count");
        
        List<QuestionRequest> questions = genAIService.generateQuestions(topic, difficulty, count);
        return ResponseEntity.ok(questions);
    }
}
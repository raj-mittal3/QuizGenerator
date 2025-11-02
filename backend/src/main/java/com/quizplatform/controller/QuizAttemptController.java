package com.quizplatform.controller;

import com.quizplatform.entity.QuizAttempt;
import com.quizplatform.service.QuizAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*")
public class QuizAttemptController {
    @Autowired
    private QuizAttemptService attemptService;

    @PostMapping("/start/{quizId}")
    public ResponseEntity<QuizAttempt> startQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(attemptService.startQuiz(quizId));
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<QuizAttempt> submitQuiz(@PathVariable Long id, @RequestBody Map<Long, Long> answers) {
        return ResponseEntity.ok(attemptService.submitQuiz(id, answers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizAttempt> getAttempt(@PathVariable Long id) {
        return ResponseEntity.ok(attemptService.getAttemptById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuizAttempt>> getUserAttempts(@PathVariable Long userId) {
        return ResponseEntity.ok(attemptService.getUserAttempts(userId));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<QuizAttempt>> getLeaderboard() {
        return ResponseEntity.ok(attemptService.getLeaderboard());
    }
}
package com.quizplatform.service;

import com.quizplatform.entity.*;
import com.quizplatform.repository.QuizAttemptRepository;
import com.quizplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class QuizAttemptService {
    @Autowired
    private QuizAttemptRepository attemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizService quizService;

    @Transactional
    public QuizAttempt startQuiz(Long quizId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Quiz quiz = quizService.getQuizById(quizId);

        QuizAttempt attempt = new QuizAttempt();
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setTotalQuestions(quiz.getQuestions().size());
        attempt.setStatus(QuizAttempt.AttemptStatus.IN_PROGRESS);

        return attemptRepository.save(attempt);
    }

    @Transactional
    public QuizAttempt submitQuiz(Long attemptId, Map<Long, Long> answers) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        attempt.setEndTime(LocalDateTime.now());
        attempt.setStatus(QuizAttempt.AttemptStatus.COMPLETED);

        int score = calculateScore(attempt.getQuiz(), answers);
        attempt.setScore(score);

        return attemptRepository.save(attempt);
    }

    private int calculateScore(Quiz quiz, Map<Long, Long> answers) {
        int score = 0;
        for (Question question : quiz.getQuestions()) {
            Long selectedOptionId = answers.get(question.getId());
            if (selectedOptionId != null) {
                QuestionOption correctOption = question.getOptions().stream()
                        .filter(QuestionOption::getIsCorrect)
                        .findFirst()
                        .orElse(null);
                
                if (correctOption != null && correctOption.getId().equals(selectedOptionId)) {
                    score += question.getPoints();
                }
            }
        }
        return score;
    }

    public List<QuizAttempt> getUserAttempts(Long userId) {
        return attemptRepository.findByUserIdOrderByStartTimeDesc(userId);
    }

    public List<QuizAttempt> getLeaderboard() {
        return attemptRepository.findTopScores();
    }

    public QuizAttempt getAttemptById(Long id) {
        return attemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));
    }
}
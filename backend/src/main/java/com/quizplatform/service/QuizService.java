package com.quizplatform.service;

import com.quizplatform.dto.QuizRequest;
import com.quizplatform.entity.*;
import com.quizplatform.repository.QuizRepository;
import com.quizplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Quiz> getAllActiveQuizzes() {
        return quizRepository.findByIsActiveTrue();
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @Transactional
    public Quiz createQuiz(QuizRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setCategory(request.getCategory());
        quiz.setDifficulty(request.getDifficulty());
        quiz.setDurationMinutes(request.getDurationMinutes());
        quiz.setCreatedBy(user);

        Quiz savedQuiz = quizRepository.save(quiz);

        if (request.getQuestions() != null) {
            List<Question> questions = request.getQuestions().stream()
                    .map(qr -> {
                        Question question = new Question();
                        question.setQuiz(savedQuiz);
                        question.setQuestionText(qr.getQuestionText());
                        question.setQuestionType(qr.getQuestionType());
                        question.setDifficulty(qr.getDifficulty());
                        question.setPoints(qr.getPoints());

                        if (qr.getOptions() != null) {
                            List<QuestionOption> options = qr.getOptions().stream()
                                    .map(or -> {
                                        QuestionOption option = new QuestionOption();
                                        option.setQuestion(question);
                                        option.setOptionText(or.getOptionText());
                                        option.setIsCorrect(or.getIsCorrect());
                                        return option;
                                    }).collect(Collectors.toList());
                            question.setOptions(options);
                        }
                        return question;
                    }).collect(Collectors.toList());
            savedQuiz.setQuestions(questions);
        }

        return quizRepository.save(savedQuiz);
    }

    public Quiz updateQuiz(Long id, QuizRequest request) {
        Quiz quiz = getQuizById(id);
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setCategory(request.getCategory());
        quiz.setDifficulty(request.getDifficulty());
        quiz.setDurationMinutes(request.getDurationMinutes());
        quiz.setUpdatedAt(LocalDateTime.now());
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        Quiz quiz = getQuizById(id);
        quiz.setIsActive(false);
        quizRepository.save(quiz);
    }

    public List<String> getCategories() {
        return quizRepository.findDistinctCategories();
    }
}
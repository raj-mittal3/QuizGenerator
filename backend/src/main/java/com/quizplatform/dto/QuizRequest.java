package com.quizplatform.dto;

import com.quizplatform.entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class QuizRequest {
    @NotBlank
    private String title;
    private String description;
    private String category;
    private Quiz.Difficulty difficulty;
    
    @Positive
    private Integer durationMinutes;
    
    private List<QuestionRequest> questions;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Quiz.Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Quiz.Difficulty difficulty) { this.difficulty = difficulty; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public List<QuestionRequest> getQuestions() { return questions; }
    public void setQuestions(List<QuestionRequest> questions) { this.questions = questions; }
}
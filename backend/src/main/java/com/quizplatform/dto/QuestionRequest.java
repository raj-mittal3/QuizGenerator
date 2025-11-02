package com.quizplatform.dto;

import com.quizplatform.entity.Question;
import com.quizplatform.entity.Quiz;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class QuestionRequest {
    @NotBlank
    private String questionText;
    private Question.QuestionType questionType;
    private Quiz.Difficulty difficulty;
    private Integer points;
    private List<OptionRequest> options;

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public Question.QuestionType getQuestionType() { return questionType; }
    public void setQuestionType(Question.QuestionType questionType) { this.questionType = questionType; }

    public Quiz.Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Quiz.Difficulty difficulty) { this.difficulty = difficulty; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public List<OptionRequest> getOptions() { return options; }
    public void setOptions(List<OptionRequest> options) { this.options = options; }
}
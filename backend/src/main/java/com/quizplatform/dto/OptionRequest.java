package com.quizplatform.dto;

import jakarta.validation.constraints.NotBlank;

public class OptionRequest {
    @NotBlank
    private String optionText;
    private Boolean isCorrect;

    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }

    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
}
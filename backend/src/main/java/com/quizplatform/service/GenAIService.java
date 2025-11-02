package com.quizplatform.service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizplatform.dto.QuestionRequest;
import com.quizplatform.dto.OptionRequest;
import com.quizplatform.entity.Question;
import com.quizplatform.entity.Quiz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GenAIService {
    
    @Value("${azure.openai.endpoint}")
    private String endpoint;
    
    @Value("${azure.openai.api-key}")
    private String apiKey;
    
    @Value("${azure.openai.deployment-name}")
    private String deploymentName;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<QuestionRequest> generateQuestions(String topic, Quiz.Difficulty difficulty, int count) {
        try {
            OpenAIClient client = new OpenAIClientBuilder()
                    .endpoint(endpoint)
                    .credential(new AzureKeyCredential(apiKey))
                    .buildClient();

            String prompt = buildPrompt(topic, difficulty, count);
            
            ChatCompletionsOptions options = new ChatCompletionsOptions(Arrays.asList(
                new ChatRequestSystemMessage("You are a quiz generator that creates educational questions."),
                new ChatRequestUserMessage(prompt)
            ));
            
            ChatCompletions completions = client.getChatCompletions(deploymentName, options);
            String response = completions.getChoices().get(0).getMessage().getContent();
            
            return parseQuestionsFromResponse(response, difficulty);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate questions: " + e.getMessage());
        }
    }

    private String buildPrompt(String topic, Quiz.Difficulty difficulty, int count) {
        return String.format(
            "Generate %d multiple-choice questions about %s with %s difficulty level. " +
            "Format the response as JSON with this structure: " +
            "{ \"questions\": [{ \"questionText\": \"...\", \"options\": [" +
            "{ \"optionText\": \"...\", \"isCorrect\": true/false }] }] }. " +
            "Each question should have 4 options with exactly one correct answer.",
            count, topic, difficulty.toString().toLowerCase()
        );
    }

    private List<QuestionRequest> parseQuestionsFromResponse(String response, Quiz.Difficulty difficulty) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode questionsNode = root.get("questions");
            
            List<QuestionRequest> questions = new ArrayList<>();
            
            for (JsonNode questionNode : questionsNode) {
                QuestionRequest question = new QuestionRequest();
                question.setQuestionText(questionNode.get("questionText").asText());
                question.setQuestionType(Question.QuestionType.MULTIPLE_CHOICE);
                question.setDifficulty(difficulty);
                question.setPoints(getPointsByDifficulty(difficulty));
                
                List<OptionRequest> options = new ArrayList<>();
                JsonNode optionsNode = questionNode.get("options");
                
                for (JsonNode optionNode : optionsNode) {
                    OptionRequest option = new OptionRequest();
                    option.setOptionText(optionNode.get("optionText").asText());
                    option.setIsCorrect(optionNode.get("isCorrect").asBoolean());
                    options.add(option);
                }
                
                question.setOptions(options);
                questions.add(question);
            }
            
            return questions;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage());
        }
    }

    private Integer getPointsByDifficulty(Quiz.Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 1;
            case MEDIUM -> 2;
            case HARD -> 3;
        };
    }
}
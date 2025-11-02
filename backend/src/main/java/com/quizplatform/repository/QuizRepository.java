package com.quizplatform.repository;

import com.quizplatform.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByIsActiveTrue();
    List<Quiz> findByCategory(String category);
    List<Quiz> findByCreatedById(Long userId);
    
    @Query("SELECT DISTINCT q.category FROM Quiz q WHERE q.isActive = true")
    List<String> findDistinctCategories();
}
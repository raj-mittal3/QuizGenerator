package com.quizplatform.repository;

import com.quizplatform.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUserIdOrderByStartTimeDesc(Long userId);
    List<QuizAttempt> findByQuizIdOrderByScoreDesc(Long quizId);
    
    @Query("SELECT qa FROM QuizAttempt qa WHERE qa.status = 'COMPLETED' ORDER BY qa.score DESC, qa.endTime ASC")
    List<QuizAttempt> findTopScores();
    
    @Query("SELECT AVG(qa.score) FROM QuizAttempt qa WHERE qa.user.id = :userId AND qa.status = 'COMPLETED'")
    Double findAverageScoreByUserId(Long userId);
}
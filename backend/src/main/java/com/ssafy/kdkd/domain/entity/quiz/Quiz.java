package com.ssafy.kdkd.domain.entity.quiz;

import com.ssafy.kdkd.domain.common.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Lob
    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private boolean answer;

    /**
     * 퀴즈 생성
     */
    public static Quiz createQuiz(Category category, String question, boolean answer) {
        Quiz quiz = new Quiz();
        quiz.category = category;
        quiz.question = question;
        quiz.answer = answer;
        return quiz;
    }

}

package com.ssafy.kdkd.service.quiz;

import com.ssafy.kdkd.domain.entity.quiz.Quiz;
import com.ssafy.kdkd.repository.quiz.QuizRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

}

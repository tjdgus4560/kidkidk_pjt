package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.domain.entity.quiz.Quiz;
import com.ssafy.kdkd.service.quiz.QuizService;

import static com.ssafy.kdkd.exception.ExceptionHandler.exceptionHandling;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quiz")
    @Operation(summary = "퀴즈 조회")
    public ResponseEntity<?> list() {
        try {
            log.info("quiz list");
            List<Quiz> result = quizService.findAll();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(header).body(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

}

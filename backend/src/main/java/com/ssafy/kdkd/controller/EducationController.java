package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.domain.entity.education.Education;
import com.ssafy.kdkd.service.education.EducationService;

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
public class EducationController {

    private final EducationService educationService;

    @GetMapping("/education")
    @Operation(summary = "경제 교육 컨텐츠 전체 조회")
    public ResponseEntity<?> list() {
        try {
            log.info("education list");
            List<Education> result = educationService.findAll();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(header).body(result);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

}

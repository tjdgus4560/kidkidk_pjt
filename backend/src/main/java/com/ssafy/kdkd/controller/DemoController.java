package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.service.fund.FundUpdateService;
import com.ssafy.kdkd.service.job.JobService;
import com.ssafy.kdkd.service.saving.SavingService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/demo")
public class DemoController {

    private final SavingService savingService;
    private final FundUpdateService fundUpdateService;
    private final JobService jobService;

    @GetMapping("/job")
    @Operation(summary = "직업 스케줄러 강제 실행")
    public ResponseEntity<?> jobScheduler() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("직업 스케줄러 강제 실행");
            jobService.updateJob();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/fund")
    @Operation(summary = "투자 스케줄러 강제 실행")
    public ResponseEntity<?> fundScheduler() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("투자 스케줄러 강제 실행");
            fundUpdateService.updateFund();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/saving")
    @Operation(summary = "적금 스케줄러 강제 실행")
    public ResponseEntity<?> savingScheduler() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("적금 스케줄러 강제 실행");
            savingService.updateSaving();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}

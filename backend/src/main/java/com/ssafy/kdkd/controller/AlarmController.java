package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.domain.dto.alarm.ExchangeRequestDto;
import com.ssafy.kdkd.domain.dto.alarm.JobRequestDto;
import com.ssafy.kdkd.service.alarm.AlarmService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @PutMapping("/job")
    @Operation(summary = "부모 직업 완료 요청 확인")
    public ResponseEntity<?> confirmTask(@RequestBody JobRequestDto jobRequestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("fund controller: confirmTask() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                alarmService.confirmTask(jobRequestDto);
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/exchange")
    @Operation(summary = "부모 환전 요청 확인")
    public ResponseEntity<?> confirmExchange(@RequestBody ExchangeRequestDto exchangeRequestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            log.info("fund controller: confirmExchange() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                if (alarmService.confirmExchange(exchangeRequestDto)) {
                    status = HttpStatus.OK;
                };
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}

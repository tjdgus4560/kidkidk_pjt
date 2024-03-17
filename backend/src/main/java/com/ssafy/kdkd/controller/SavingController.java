package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.domain.dto.saving.SavingDto;
import com.ssafy.kdkd.domain.dto.saving.SavingHistoryDto;
import com.ssafy.kdkd.domain.entity.saving.Saving;
import com.ssafy.kdkd.domain.entity.saving.SavingHistory;
import com.ssafy.kdkd.repository.saving.SavingRepository;
import com.ssafy.kdkd.service.saving.SavingHistoryService;
import com.ssafy.kdkd.service.saving.SavingService;

import static com.ssafy.kdkd.domain.dto.saving.SavingDto.mappingSavingDto;
import static com.ssafy.kdkd.domain.dto.saving.SavingHistoryDto.mappingSavingHistoryDto;
import static com.ssafy.kdkd.exception.ExceptionHandler.exceptionHandling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/saving")
public class SavingController {

    private final SavingService savingService;
    private final SavingRepository savingRepository;
    private final SavingHistoryService savingHistoryService;

    @GetMapping("/info/{childId}")
    @Operation(summary = "적금 조회")
    public ResponseEntity<?> info(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("saving controller: info() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                Optional<Saving> result = savingRepository.findById(childId);
                if (result.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    SavingDto savingDto = mappingSavingDto(result.get());
                    resultMap.put("Saving", savingDto);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/create")
    @Operation(summary = "적금 생성")
    public ResponseEntity<?> create(@RequestBody SavingDto savingDto, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            log.info("saving controller: create() Enter");
            Long childId = savingDto.getChildId();
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                SavingDto reslut =  savingService.createSaving(savingDto);
                if (reslut == null) {
                    status = HttpStatus.CONFLICT;
                } else {
                    resultMap.put("Saving", reslut);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/history/{childId}")
    @Operation(summary = "적금 내역 조회")
    public ResponseEntity<?> history(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("saving controller: history() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                List<SavingHistory> savingHistoryList =
                    savingHistoryService.findSavingHistoriesByChildId(childId);

                if (savingHistoryList == null) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    List<SavingHistoryDto> result =
                        mappingSavingHistoryDto(savingHistoryList);
                    resultMap.put("SavingHistories", result);
                }
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}

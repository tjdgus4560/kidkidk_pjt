package com.ssafy.kdkd.controller;

import com.ssafy.kdkd.domain.dto.deposit.DepositDto;
import com.ssafy.kdkd.domain.entity.deposit.Deposit;
import com.ssafy.kdkd.service.deposit.DepositService;

import static com.ssafy.kdkd.domain.dto.deposit.DepositDto.mappingDepositDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/deposit")
public class DepositController {

    private final DepositService depositService;

    @GetMapping("/history/{childId}")
    @Operation(summary = "예금 내역 조회")
    public ResponseEntity<?> list(@PathVariable("childId") Long childId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            log.info("deposit controller: list() Enter");
            // 현재 childId에 대한 권한 확인
            boolean isValid = false;

            if (isValid) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                List<Deposit> deposits = depositService.findDepositsByChildId(childId);
                if (deposits.isEmpty()) {
                    status = HttpStatus.NO_CONTENT;
                } else {
                    List<DepositDto> depositDtoList = mappingDepositDto(deposits);
                    resultMap.put("DepositList", depositDtoList);
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}

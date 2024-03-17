package com.ssafy.kdkd.controller;

import static com.ssafy.kdkd.exception.ExceptionHandler.exceptionHandling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.kdkd.service.account.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class UsersController {

    private final UserService userService;

    @GetMapping("/userinfo")
    @Operation(summary = "User 정보 조회")
    public ResponseEntity<?> userInfo(HttpServletRequest request, Authentication authentication) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            if (authentication == null) {
                status = HttpStatus.UNAUTHORIZED;
            } else {
                resultMap.put("userinfo", userService.getUser(authentication.getName()));
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
        return new ResponseEntity<>(resultMap, status);
    }
}

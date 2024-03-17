package com.ssafy.kdkd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandler extends Exception {
    public static ResponseEntity<String> exceptionHandling(Exception e) {
        log.info(e.toString());
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

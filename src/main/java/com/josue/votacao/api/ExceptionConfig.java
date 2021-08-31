package com.josue.votacao.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity throwBadRequest(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}

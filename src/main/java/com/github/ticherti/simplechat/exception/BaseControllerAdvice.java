package com.github.ticherti.simplechat.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseControllerAdvice {

    @ExceptionHandler(AbstractNotFoundException.class)
    public ResponseEntity messageNotFound(AbstractNotFoundException ex) {
        return response(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity methodArgumentNotValid(MethodArgumentNotValidException ex) {
        return response(HttpStatus.BAD_REQUEST, ex);
    }

    private ResponseEntity response(HttpStatus status, Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", status.toString());
        body.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(body, headers, status);
    }
}
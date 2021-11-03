package com.urlshortener.api.application.handler;

import com.urlshortener.api.support.ShortenerError;
import com.urlshortener.api.support.ShortenerException;
import com.urlshortener.api.support.ShortenerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ControllerAdvice // 추가
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ShortenerException.class)
    public ResponseEntity<ShortenerResponse<?>> ShortenerException(ShortenerException e) {
        ShortenerError error = e.getError();
        log.error("ShortenerException : " + error.getDesc());

        return ResponseEntity
                .status(error.getStatus())
                .body(new ShortenerResponse<>(error.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ShortenerResponse<?>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ShortenerResponse<>(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ShortenerResponse<?>> unknownException(Exception e) {
        log.error("UnknownException : " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ShortenerResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ShortenerError.UNKNOWN_ERROR.getDesc()));
    }
}

package com.urlshortener.api.application.handler;

import com.urlshortener.api.dto.ErrorDto;
import com.urlshortener.api.support.ShortenerError;
import com.urlshortener.api.support.ShortenerException;
import com.urlshortener.api.support.ShortenerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ShortenerException.class)
    public ModelAndView ShortenerException(ShortenerException e) {
        ModelAndView mav = new ModelAndView();
        ShortenerError error = e.getError();
        log.error("ShortenerException : " + error.getDesc());

        ErrorDto errorDto = new ErrorDto(e.getMessage());
        mav.addObject("data", errorDto);
        mav.setViewName("error");

        return mav;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ShortenerResponse<?>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
        log.error("MethodArgumentNotValidException : " + errorMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ShortenerResponse<>(HttpStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ShortenerResponse<?>> unknownException(Exception e) {
        log.error("UnknownException : " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ShortenerResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ShortenerError.UNKNOWN_ERROR.getDesc()));
    }
}

package com.learnkafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;


// Here we are consolidating the error message and present user readable message.
// This annoatation intercept with error message and we can add custom error messages to that exception.
@ControllerAdvice
@Slf4j
public class LibraryEventsControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex){

        var errorMessage = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + " _" + fieldError.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(","));

        log.info("errorMessage :" + errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}

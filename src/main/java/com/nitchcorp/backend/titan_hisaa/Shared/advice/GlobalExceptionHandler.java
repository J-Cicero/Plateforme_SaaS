package com.nitchcorp.backend.titan_hisaa.Shared.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public @ResponseBody ExceptionAdviceResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return new ExceptionAdviceResponse(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", e.getMessage());
    }


}
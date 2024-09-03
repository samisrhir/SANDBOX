package com.hps.sandbox.service.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException exception, final HttpServletRequest request) {

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ExceptionResponse error = ExceptionResponse.builder()
                .errorMessage(exception.getMessage())
                .errorKey(String.valueOf(notFound.value()))
                .requestedURI(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, notFound);
    }

}

package com.ninjaone.dundieawards.activity.infrastructure.rest;

import com.ninjaone.dundieawards.common.infrastructure.security.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(
            ResponseStatusException e
    ) {
        var status = HttpStatus.valueOf(e.getStatusCode().value());

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        "An unexpected error occurred. Please try again later."
                ),
                status
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AuthorizationDeniedException e) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.FORBIDDEN,
                        "You do not have permission to access this resource."
                ),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> fallBackHandleExceptions(
            Exception e
    ) {

        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        "An unexpected error occurred. Please try again later."
                ),
                status
        );
    }
}

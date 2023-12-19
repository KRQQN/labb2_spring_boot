package com.example.labb2_spring_boot.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException e) {

        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail
                        (HttpStatus.NOT_FOUND, e.getLocalizedMessage());

        problemDetails.setTitle(STR."\{e.getType()} not found");
        problemDetails.setProperty(e.getType(), e.getId());
        return problemDetails;
    }
}

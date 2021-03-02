/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.navan.alerttracker.system;

import io.navan.alerttracker.AlertController;
import io.navan.alerttracker.model.Alert;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author tonybrouwer
 */
@ControllerAdvice(basePackageClasses = AlertController.class)
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(
            ControllerExceptionHandler.class);
    
    @ExceptionHandler( {EntityNotFoundException.class} )
    public ResponseEntity<ApiError> handleNotFound(
            EntityNotFoundException enfException, WebRequest req) {
        var apiError = new ApiError(NOT_FOUND
                , "Resource not found"
                , new ApiError.Error(String.format("%s not found", enfException.getMessage())
                        , "*"
                        , String.format("Resource not found"))
        );
        return new ResponseEntity<>(apiError
                , new HttpHeaders()
                , NOT_FOUND
                );
    }
    
    @ExceptionHandler( {ConstraintViolationException.class} )
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException cvException
            , WebRequest req) {
        List<ApiError.Error> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation:
                cvException.getConstraintViolations()) {
            errors.add(new ApiError.Error(
                    violation.getRootBeanClass().getName()
                    , violation.getPropertyPath().toString()
                    , violation.getMessage()));
        }
        var apiError = new ApiError(BAD_REQUEST, "Validation Errors", errors);
        
        return new ResponseEntity<>(apiError
                , new HttpHeaders()
                , apiError.getHttpStatus());
    }
    
}

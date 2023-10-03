package com.example.chocolatefactory.config;

import com.example.chocolatefactory.domain.records.ErrorDTO;
import com.example.chocolatefactory.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleException(AppException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorDTO(ex.getMessage()));
    }
}

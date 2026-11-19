package com.employee.salary_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSalaryRevisionException.class)
    public ResponseEntity<String> handleInvalidSlaryRevisionTypeException(InvalidSalaryRevisionException  ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(SalaryNotFoundException.class)
    public ResponseEntity<String> hanldeSalaryNotFoundException(SalaryNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SalaryAlreadyExistsException.class)
    public ResponseEntity<String> handleSalaryAlreadyExistsException(SalaryAlreadyExistsException ex){
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach( error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}

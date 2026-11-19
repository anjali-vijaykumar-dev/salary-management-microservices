package com.employee.salary_service.exception;

public class SalaryNotFoundException extends RuntimeException {
    public SalaryNotFoundException(String message) {
        super(message);
    }
}

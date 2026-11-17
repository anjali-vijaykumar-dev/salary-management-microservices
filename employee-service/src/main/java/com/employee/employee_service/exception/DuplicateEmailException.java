package com.employee.employee_service.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Employee already exists with the email: "+ email);
    }
}

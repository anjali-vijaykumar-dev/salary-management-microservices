package com.employee.employee_service.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Long id) {
        super("Emplyee not found with id: " +id);
    }
}

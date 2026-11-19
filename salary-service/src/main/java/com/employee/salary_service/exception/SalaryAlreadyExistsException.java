package com.employee.salary_service.exception;

import jakarta.validation.constraints.NotNull;

public class SalaryAlreadyExistsException extends RuntimeException {
    public SalaryAlreadyExistsException(Long employeeId) {
        super("Salary already exists for employee id: "+ employeeId);
    }
}

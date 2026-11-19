package com.employee.salary_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateSalaryRequest {

    @NotNull(message = "Employee id is required")
    private Long employeeId;

    @NotNull(message = "Annual salary is required")
    @Positive(message = "Annual salary must be greater than 0")
    private BigDecimal annualSalary;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter code like INR, USD, EUR")
    private String currency;

    @NotNull(message = "Effective date is required")
    private LocalDate effectiveFrom;
}
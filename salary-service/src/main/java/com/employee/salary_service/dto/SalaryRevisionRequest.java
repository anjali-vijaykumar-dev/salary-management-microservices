package com.employee.salary_service.dto;

import com.employee.salary_service.entity.RevisonType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.history.RevisionMetadata;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalaryRevisionRequest {

    @NotNull(message = "New annual salary is required")
    @Positive(message = "Annual salary must be greater than 0")
    private BigDecimal newAnnualSalary;

    @NotNull(message = "Effective date is required")
    private LocalDate effectiveFrom;

    @NotNull(message = "Revision type is required")
    private RevisonType revisionType;

    private String remarks;
}
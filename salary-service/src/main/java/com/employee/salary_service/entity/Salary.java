package com.employee.salary_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "employee_id, effective_from"}))
@NoArgsConstructor
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal annualSalary;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RevisonType revisionType;

    @Column(precision = 5, scale = 2)
    private BigDecimal hikePercentage;

    private String remarks;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}

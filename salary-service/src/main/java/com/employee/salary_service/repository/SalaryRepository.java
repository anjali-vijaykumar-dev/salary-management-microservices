package com.employee.salary_service.repository;

import com.employee.salary_service.entity.Salary;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    boolean existsByEmployeeId( Long employeeId);

    Optional<Salary> findTopByEmployeeIdOrderByEffectiveFromDesc(Long employeeId);

    @Query("""
            select s from Salary s where s.employeeId = :employeeId AND s.effectiveFrom <= :date
            AND (s.effectiveTo IS NULL  OR s.effectiveTo >= :date)
            """)
    Optional<Salary> findActive(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);

    List<Salary> findByEmployeeIdOrderByEffectiveFromDesc(Long employeeId);
}

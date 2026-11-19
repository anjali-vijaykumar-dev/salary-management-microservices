package com.employee.salary_service.service;

import com.employee.salary_service.dto.CreateSalaryRequest;
import com.employee.salary_service.dto.SalaryRevisionRequest;
import com.employee.salary_service.entity.Salary;
import com.employee.salary_service.exception.InvalidSalaryRevisionException;
import com.employee.salary_service.exception.SalaryAlreadyExistsException;
import com.employee.salary_service.exception.SalaryNotFoundException;
import com.employee.salary_service.repository.SalaryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.employee.salary_service.entity.RevisonType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepo;

    public Salary createJoiningSalary(CreateSalaryRequest request) {
        if (salaryRepo.existsByEmployeeId(request.getEmployeeId())) {
            throw new SalaryAlreadyExistsException(request.getEmployeeId());
        }
        Salary salary = new Salary();
        salary.setEmployeeId(request.getEmployeeId());
        salary.setAnnualSalary(request.getAnnualSalary());
        salary.setCurrency(request.getCurrency());
        salary.setEffectiveFrom(request.getEffectiveFrom());
        salary.setRevisionType(RevisonType.JOINING);
        return salaryRepo.save(salary);

    }

    @Transactional
    public Salary reviseSalary(Long employeeId,  SalaryRevisionRequest request) {
        if (request.getRevisionType() == RevisonType.JOINING) {
            throw new InvalidSalaryRevisionException("Joining is for only first salary");
        }

        Salary latestSalary = salaryRepo.findTopByEmployeeIdOrderByEffectiveFromDesc(employeeId).
                orElseThrow(() -> new SalaryNotFoundException("No salary found for employee id: " + employeeId +
                        ". create joining salary first"));

        boolean mustIncrease = request.getRevisionType() == RevisonType.PROMOTION || request.getRevisionType() == RevisonType.ANNUAL_HIKE;
        if (mustIncrease && request.getNewAnnualSalary().compareTo(latestSalary.getAnnualSalary()) <= 0) {
            throw new InvalidSalaryRevisionException(request.getRevisionType() + " must increase above " + latestSalary.getAnnualSalary());
        }

        if (!request.getEffectiveFrom().isAfter(latestSalary.getEffectiveFrom())) {
            throw new InvalidSalaryRevisionException(
                    "Effective date must be after " + latestSalary.getEffectiveFrom());
        }

        latestSalary.setEffectiveTo(request.getEffectiveFrom().minusDays(1));
        salaryRepo.save(latestSalary);

        Salary revised = new Salary();
        revised.setEmployeeId(employeeId);
        revised.setAnnualSalary(request.getNewAnnualSalary());
        revised.setCurrency(latestSalary.getCurrency());
        revised.setRemarks(request.getRemarks());
        revised.setEffectiveFrom(request.getEffectiveFrom());
        revised.setRevisionType(request.getRevisionType());
        revised.setHikePercentage(calculateHikePercentage(latestSalary.getAnnualSalary(), request.getNewAnnualSalary()));
        return salaryRepo.save(revised);
    }

    private BigDecimal calculateHikePercentage(BigDecimal oldSalary, BigDecimal newAnnualSalary) {
        return newAnnualSalary.subtract(oldSalary).multiply(BigDecimal.valueOf(100)).divide(oldSalary, 2, RoundingMode.HALF_UP);
    }

    public Salary getCurrentSalary(Long employeeId) {
        return salaryRepo.findActive(employeeId, LocalDate.now()).orElseThrow(() -> new SalaryNotFoundException("No active salary for employee id " + employeeId));
    }

    public List<Salary> getSlaryHistory(Long employeeId) {
        return salaryRepo.findByEmployeeIdOrderByEffectiveFromDesc(employeeId);
    }

    public Salary getSalary(Long id) {
        return salaryRepo.findById(id).orElseThrow(() -> new SalaryNotFoundException("Salary not found for the id: " + id));
    }
}

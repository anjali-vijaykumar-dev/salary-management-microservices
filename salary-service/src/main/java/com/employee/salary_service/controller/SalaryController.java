package com.employee.salary_service.controller;

import com.employee.salary_service.dto.CreateSalaryRequest;
import com.employee.salary_service.dto.SalaryRevisionRequest;
import com.employee.salary_service.entity.Salary;
import com.employee.salary_service.service.SalaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @PostMapping
    public ResponseEntity<Salary> createJoiningSalary(@Valid @RequestBody CreateSalaryRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(salaryService.createJoiningSalary(request));
    }

    @PostMapping("employee/{employeeId}/revisions")
    public ResponseEntity<Salary> reviseSalary(@PathVariable Long employeeId, @Valid @RequestBody SalaryRevisionRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(salaryService.reviseSalary(employeeId, request));
    }

    @GetMapping("/employee/{employeeId}/current")
    public ResponseEntity<Salary> getCurrentSalary(@PathVariable Long employeeId){
        return ResponseEntity.ok(salaryService.getCurrentSalary(employeeId));
    }

    @GetMapping("/employee/{employeeId}/history")
    public  ResponseEntity<List<Salary>> getSalaryHistory(@PathVariable Long employeeId){
        return ResponseEntity.ok(salaryService.getSlaryHistory(employeeId));
    }

    @GetMapping("{id}")
    public ResponseEntity<Salary> getSalary(@PathVariable Long id){
        return ResponseEntity.ok(salaryService.getSalary(id));
    }
}

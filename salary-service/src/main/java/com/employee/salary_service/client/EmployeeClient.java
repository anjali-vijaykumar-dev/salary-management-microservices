package com.employee.salary_service.client;

import com.employee.salary_service.dto.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service", url = "${employee-service.url}")
public interface EmployeeClient {

    @GetMapping("/employees/{id}")
    EmployeeResponse getEmployeeById(@PathVariable("id") Long id);
}

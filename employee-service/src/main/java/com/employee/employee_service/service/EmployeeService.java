package com.employee.employee_service.service;

import com.employee.employee_service.entity.Employee;
import com.employee.employee_service.exception.DuplicateEmailException;
import com.employee.employee_service.exception.EmployeeNotFoundException;
import com.employee.employee_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    EmployeeService(EmployeeRepository repository){
        this.repository = repository;

    }

    public Employee createEmployee(Employee employee) {
       if(repository.existsByEmail(employee.getEmail())){
           throw new DuplicateEmailException(employee.getEmail());
        }
        return repository.save(employee);

    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)) ;
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        existingEmployee.setName(employee.getName());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setDesignation(employee.getDesignation());
        if (!existingEmployee.getEmail().equals(employee.getEmail())
                && repository.existsByEmail(employee.getEmail())) {
            throw new DuplicateEmailException(employee.getEmail());
        }
        existingEmployee.setEmail(employee.getEmail());

        return repository.save(existingEmployee);
    }
}

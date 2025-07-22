package com.example.EmpApp.Service;

import com.example.EmpApp.dto.EmployeeDTO;
import com.example.EmpApp.Entity.Employee;

import java.util.List;

public interface EmployeeServiceInterface {
    Employee create(EmployeeDTO dto);
    List<Employee> getAll();
    Employee getById(Long id);
    Employee update(Long id, EmployeeDTO dto);
    void delete(Long id);
}

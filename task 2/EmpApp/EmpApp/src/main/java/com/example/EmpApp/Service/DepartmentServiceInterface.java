package com.example.EmpApp.Service;

import com.example.EmpApp.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentServiceInterface {
    DepartmentDTO createDepartment(DepartmentDTO dto);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartmentById(Long id);
    DepartmentDTO updateDepartment(Long id, DepartmentDTO dto);
    void deleteDepartment(Long id);
}

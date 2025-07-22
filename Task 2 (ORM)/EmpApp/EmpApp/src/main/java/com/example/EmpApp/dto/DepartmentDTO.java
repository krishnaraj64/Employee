package com.example.EmpApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {
    private Long deptId;

    @NotBlank(message = "Department name is required")
    private String deptName;

    private List<EmployeeDTO> employees;
}

package com.example.EmpApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Project title is required")
    private String title;

    private List<Long> employeeIds; // Sequential, clean list
}

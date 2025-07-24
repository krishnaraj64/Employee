package com.example.EmpApp.Service;

import com.example.EmpApp.dto.ProjectDTO;

import java.util.List;

public interface ProjectServiceInterface {
    ProjectDTO create(ProjectDTO dto);
    List<ProjectDTO> getAll();
    ProjectDTO getById(Long id);
    ProjectDTO update(Long id, ProjectDTO dto);
    void delete(Long id);
}

package com.example.EmpApp.Service;

import com.example.EmpApp.Entity.Employee;
import com.example.EmpApp.Entity.Project;
import com.example.EmpApp.Repository.EmployeeRepository;
import com.example.EmpApp.Repository.ProjectRepository;
import com.example.EmpApp.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService implements ProjectServiceInterface {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public ProjectDTO create(ProjectDTO dto) {
        Set<Employee> employees = new HashSet<>();
        if (dto.getEmployeeIds() != null && !dto.getEmployeeIds().isEmpty()) {
            employees.addAll(employeeRepo.findAllById(dto.getEmployeeIds()));
        }

        Project project = new Project();
        project.setTitle(dto.getTitle());
        project.setEmployees(employees);

        return toDTO(projectRepo.save(project));
    }

    @Override
    public List<ProjectDTO> getAll() {
        return projectRepo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getById(Long id) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Project not found"));
        return toDTO(project);
    }

    @Override
    public ProjectDTO update(Long id, ProjectDTO dto) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Project not found"));

        Set<Employee> employees = new HashSet<>();
        if (dto.getEmployeeIds() != null && !dto.getEmployeeIds().isEmpty()) {
            employees.addAll(employeeRepo.findAllById(dto.getEmployeeIds()));
        }

        project.setTitle(dto.getTitle());
        project.setEmployees(employees);

        return toDTO(projectRepo.save(project));
    }

    @Override
    public void delete(Long id) {
        projectRepo.deleteById(id);
    }

    private ProjectDTO toDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());

        if (project.getEmployees() != null && !project.getEmployees().isEmpty()) {
            List<Long> employeeIds = project.getEmployees().stream()
                    .map(Employee::getEmpId)
                    .sorted() // optional for sequential order
                    .collect(Collectors.toList());
            dto.setEmployeeIds(employeeIds);
        } else {
            dto.setEmployeeIds(new ArrayList<>());
        }

        return dto;
    }
}

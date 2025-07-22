package com.example.EmpApp.Service;

import com.example.EmpApp.Entity.Department;
import com.example.EmpApp.Entity.Employee;
import com.example.EmpApp.Entity.Project;
import com.example.EmpApp.Repository.DepartmentRepository;
import com.example.EmpApp.dto.AddressDTO;
import com.example.EmpApp.dto.DepartmentDTO;
import com.example.EmpApp.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService implements DepartmentServiceInterface {

    @Autowired
    private DepartmentRepository repo;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department dept = new Department();
        dept.setDeptName(dto.getDeptName());
        Department saved = repo.save(dept);

        return toDTO(saved);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department dept = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        return toDTO(dept);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO dto) {
        Department dept = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        dept.setDeptName(dto.getDeptName());
        return toDTO(repo.save(dept));
    }

    @Override
    public void deleteDepartment(Long id) {
        repo.deleteById(id);
    }

    private DepartmentDTO toDTO(Department dept) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDeptId(dept.getDeptId());
        dto.setDeptName(dept.getDeptName());

        if (dept.getEmployees() != null) {
            List<EmployeeDTO> empDTOs = dept.getEmployees().stream()
                    .map(emp -> {
                        EmployeeDTO edto = new EmployeeDTO();
                        edto.setEmpId(emp.getEmpId());
                        edto.setEmpName(emp.getEmpName());
                        edto.setEmail(emp.getEmail());
                        edto.setPhoneNo(emp.getPhoneNo());
                        edto.setPassword(emp.getPassword());

                        if (emp.getAddress() != null) {
                            AddressDTO addressDTO = new AddressDTO();
                            addressDTO.setStreet(emp.getAddress().getStreet());
                            addressDTO.setCity(emp.getAddress().getCity());
                            addressDTO.setState(emp.getAddress().getState());
                            addressDTO.setZipCode(emp.getAddress().getZipCode());
                            edto.setAddress(addressDTO);
                        }

                        edto.setDepartmentId(dept.getDeptId());

                        // ✅ NEW: include projectIds
                        if (emp.getProjects() != null && !emp.getProjects().isEmpty()) {
                            Set<Long> projectIds = emp.getProjects().stream()
                                    .map(Project::getId)
                                    .collect(Collectors.toSet());
                            edto.setProjectIds(projectIds);
                        } else {
                            edto.setProjectIds(Set.of());
                        }

                        return edto;
                    })
                    .collect(Collectors.toList());

            dto.setEmployees(empDTOs);
        } else {
            dto.setEmployees(List.of());
        }

        return dto;
    }
}

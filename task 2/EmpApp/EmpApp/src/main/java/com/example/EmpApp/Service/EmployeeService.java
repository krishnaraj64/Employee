package com.example.EmpApp.Service;

import com.example.EmpApp.dto.AddressDTO;
import com.example.EmpApp.dto.EmployeeDTO;
import com.example.EmpApp.Entity.Address;
import com.example.EmpApp.Entity.Department;
import com.example.EmpApp.Entity.Employee;
import com.example.EmpApp.Entity.Project;
import com.example.EmpApp.Repository.AddressRepository;
import com.example.EmpApp.Repository.DepartmentRepository;
import com.example.EmpApp.Repository.EmployeeRepository;
import com.example.EmpApp.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements EmployeeServiceInterface {

    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private AddressRepository addressRepo;
    @Autowired private DepartmentRepository departmentRepo;
    @Autowired private ProjectRepository projectRepo;

    @Override
    public Employee create(EmployeeDTO dto) {
        if (employeeRepo.existsByEmailAndPhoneNo(dto.getEmail(), dto.getPhoneNo())) {
            throw new RuntimeException("❌ Employee with same email and phone already exists.");
        }

        AddressDTO addrDto = dto.getAddress();
        employeeRepo.findExactDuplicate(
                dto.getEmpName(), dto.getEmail(), dto.getPhoneNo(),
                addrDto.getStreet(), addrDto.getCity(), addrDto.getState(), addrDto.getZipCode()
        ).ifPresent(e -> {
            throw new RuntimeException("❌ Duplicate employee with same contact and address exists.");
        });

        Address address = addressRepo.findByStreetAndCityAndStateAndZipCode(
                addrDto.getStreet(), addrDto.getCity(), addrDto.getState(), addrDto.getZipCode()
        ).orElseGet(() -> {
            Address newAddr = new Address();
            newAddr.setStreet(addrDto.getStreet());
            newAddr.setCity(addrDto.getCity());
            newAddr.setState(addrDto.getState());
            newAddr.setZipCode(addrDto.getZipCode());
            return newAddr;
        });

        Department dept = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("❌ Department not found"));

        Employee emp = new Employee();
        emp.setEmpName(dto.getEmpName());
        emp.setEmail(dto.getEmail());
        emp.setPhoneNo(dto.getPhoneNo());
        emp.setPassword(dto.getPassword());
        emp.setAddress(address);
        emp.setDepartment(dept);

        if (dto.getProjectIds() != null && !dto.getProjectIds().isEmpty()) {
            Set<Project> projects = new HashSet<>(projectRepo.findAllById(dto.getProjectIds()));
            emp.setProjects(projects);
        } else {
            emp.setProjects(new HashSet<>());
        }

        return employeeRepo.save(emp);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepo.findAllByOrderByEmpIdAsc();
    }

    @Override
    public Employee getById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Employee not found"));
    }

    @Override
    public Employee update(Long id, EmployeeDTO dto) {
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Employee not found"));

        AddressDTO addrDto = dto.getAddress();
        Address address = addressRepo.findByStreetAndCityAndStateAndZipCode(
                addrDto.getStreet(), addrDto.getCity(), addrDto.getState(), addrDto.getZipCode()
        ).orElseGet(() -> {
            Address newAddr = new Address();
            newAddr.setStreet(addrDto.getStreet());
            newAddr.setCity(addrDto.getCity());
            newAddr.setState(addrDto.getState());
            newAddr.setZipCode(addrDto.getZipCode());
            return newAddr;
        });

        Department dept = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("❌ Department not found"));

        emp.setEmpName(dto.getEmpName());
        emp.setEmail(dto.getEmail());
        emp.setPhoneNo(dto.getPhoneNo());
        emp.setPassword(dto.getPassword());
        emp.setAddress(address);
        emp.setDepartment(dept);

        if (dto.getProjectIds() != null) {
            Set<Project> projects = new HashSet<>(projectRepo.findAllById(dto.getProjectIds()));
            emp.setProjects(projects);
        } else {
            emp.setProjects(new HashSet<>());
        }

        return employeeRepo.save(emp);
    }

    @Override
    public void delete(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new RuntimeException("❌ Employee not found");
        }
        employeeRepo.deleteById(id);
    }
}

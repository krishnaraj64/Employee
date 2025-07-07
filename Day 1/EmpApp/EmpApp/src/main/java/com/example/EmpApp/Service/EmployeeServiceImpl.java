package com.example.EmpApp.Service;

import com.example.EmpApp.dto.EmployeeDTO;
import com.example.EmpApp.Entity.Employee;
import com.example.EmpApp.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    // 🔁 Convert DTO to Entity
    private Employee toEntity(EmployeeDTO dto) {
        Employee e = new Employee();
        e.setEmpid(dto.getEmpid());
        e.setEmpname(dto.getEmpname());
        e.setPhoneNo(dto.getPhoneNo());
        e.setEmail(dto.getEmail());
        e.setPassword(dto.getPassword());
        return e;
    }

    // 🔁 Convert Entity to DTO
    private EmployeeDTO toDTO(Employee e) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmpid(e.getEmpid());
        dto.setEmpname(e.getEmpname());
        dto.setPhoneNo(e.getPhoneNo());
        dto.setEmail(e.getEmail());
        dto.setPassword(e.getPassword());
        return dto;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public EmployeeDTO getById(Long id) {
        Employee e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        return toDTO(e);
    }

    @Override
    public EmployeeDTO create(EmployeeDTO dto) {
        Employee saved = repo.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        Employee e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        e.setEmpname(dto.getEmpname());
        e.setPhoneNo(dto.getPhoneNo());
        e.setEmail(dto.getEmail());
        e.setPassword(dto.getPassword());

        return toDTO(repo.save(e));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Cannot delete. Employee not found with ID: " + id);
        }
        repo.deleteById(id);
    }
}

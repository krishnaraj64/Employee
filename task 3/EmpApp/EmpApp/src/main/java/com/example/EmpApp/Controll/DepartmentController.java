package com.example.EmpApp.Controll;

import com.example.EmpApp.Service.DepartmentServiceInterface;
import com.example.EmpApp.dto.DepartmentDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentServiceInterface departmentService;

    @PostMapping
    public DepartmentDTO create(@Valid @RequestBody DepartmentDTO dto) {
        return departmentService.createDepartment(dto);
    }

    @GetMapping
    public List<DepartmentDTO> getAll() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentDTO getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public DepartmentDTO update(@PathVariable Long id, @Valid @RequestBody DepartmentDTO dto) {
        return departmentService.updateDepartment(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "✅ Department deleted successfully with ID: " + id);
        return ResponseEntity.ok(response);
    }
}

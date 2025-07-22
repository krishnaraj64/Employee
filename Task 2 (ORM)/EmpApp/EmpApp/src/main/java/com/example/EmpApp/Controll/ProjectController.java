package com.example.EmpApp.Controll;

import com.example.EmpApp.Service.ProjectServiceInterface;
import com.example.EmpApp.dto.ProjectDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectServiceInterface service;

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@Valid @RequestBody ProjectDTO dto) {
        ProjectDTO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAll() {
        List<ProjectDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id) {
        ProjectDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable Long id, @Valid @RequestBody ProjectDTO dto) {
        ProjectDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "✅ Project deleted successfully with ID: " + id);
        return ResponseEntity.ok(response);
    }
}

package com.example.EmpApp.Repository;

import com.example.EmpApp.Entity.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @EntityGraph(attributePaths = {"employees"})
    Optional<Department> findById(Long id);
}
package com.example.EmpApp.Repository;

import com.example.EmpApp.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}

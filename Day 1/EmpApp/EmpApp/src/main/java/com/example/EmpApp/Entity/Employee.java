package com.example.EmpApp.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empid;

    private String empname;
    private String phoneNo;
    private String email;
    private String password;
}

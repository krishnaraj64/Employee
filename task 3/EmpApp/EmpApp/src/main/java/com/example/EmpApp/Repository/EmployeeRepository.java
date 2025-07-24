package com.example.EmpApp.Repository;

import com.example.EmpApp.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    // ✅ Check if same email and phone already exist
    boolean existsByEmailAndPhoneNo(String email, String phoneNo);

    // ✅ Check if same employee with same address exists
    @Query("SELECT e FROM Employee e WHERE " +
            "e.empName = :empName AND " +
            "e.email = :email AND " +
            "e.phoneNo = :phoneNo AND " +
            "e.address.street = :street AND " +
            "e.address.city = :city AND " +
            "e.address.state = :state AND " +
            "e.address.zipCode = :zipCode")
    Optional<Employee> findExactDuplicate(
            String empName,
            String email,
            String phoneNo,
            String street,
            String city,
            String state,
            String zipCode
    );
    List<Employee> findAllByOrderByEmpIdAsc();

}

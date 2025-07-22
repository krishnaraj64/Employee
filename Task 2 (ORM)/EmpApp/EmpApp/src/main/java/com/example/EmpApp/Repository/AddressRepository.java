package com.example.EmpApp.Repository;

import com.example.EmpApp.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByStreetAndCityAndStateAndZipCode(String street, String city, String state, String zipCode);
}

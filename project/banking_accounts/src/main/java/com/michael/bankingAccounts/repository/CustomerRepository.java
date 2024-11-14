package com.michael.bankingAccounts.repository;

import com.michael.bankingAccounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByMobileNumber(String mobileNumber);

    Boolean existsByEmail(String email);

    Boolean existsByMobileNumber(String mobileNumber);

}

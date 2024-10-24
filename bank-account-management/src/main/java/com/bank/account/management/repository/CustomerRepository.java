package com.bank.account.management.repository;

import com.bank.account.management.model.BankAccount;
import com.bank.account.management.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
}

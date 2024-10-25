package com.bank.account.management.repository;

import com.bank.account.management.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @Rollback(false)
    void setUp() {
        Customer customer = new Customer();
        customer.setUsername("testuser");

        customerRepository.save(customer);
    }

    @Test
    void findByUsername_shouldReturnCustomer_whenCustomerExists() {
        // Act
        Optional<Customer> result = customerRepository.findByUsername("testuser");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void findByUsername_shouldReturnEmpty_whenCustomerDoesNotExist() {
        // Act
        Optional<Customer> result = customerRepository.findByUsername("nonexistentuser");

        // Assert
        assertThat(result).isNotPresent();
    }
}

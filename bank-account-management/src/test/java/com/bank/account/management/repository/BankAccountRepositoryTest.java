package com.bank.account.management.repository;

import com.bank.account.management.model.BankAccount;
import com.bank.account.management.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testFindByAccountNumber() {
        // Arrange
        Customer customer = new Customer();
        customer.setUsername("john_doe");
        customer.setEmail("john@example.com");
        customer.setName("John Doe");
        customer = customerRepository.save(customer);

        BankAccount account = new BankAccount();
        account.setAccountNumber("123456789");
        account.setBalance(BigDecimal.valueOf(1000.0));
        account.setCustomer(customer);
        bankAccountRepository.save(account);

        // Act
        Optional<BankAccount> foundAccount = bankAccountRepository.findByAccountNumber("123456789");

        // Assert
        assertTrue(foundAccount.isPresent());
        assertEquals("123456789", foundAccount.get().getAccountNumber());
        assertEquals(BigDecimal.valueOf(1000.0), foundAccount.get().getBalance());
    }

    @Test
    void testFindByCustomerId() {
        // Arrange
        Customer customer = new Customer();
        customer.setUsername("jane_doe");
        customer.setEmail("jane@example.com");
        customer.setName("Jane Doe");
        customer = customerRepository.save(customer);

        BankAccount account1 = new BankAccount();
        account1.setAccountNumber("111111111");
        account1.setBalance(BigDecimal.valueOf(1500.0));
        account1.setCustomer(customer);
        bankAccountRepository.save(account1);

        BankAccount account2 = new BankAccount();
        account2.setAccountNumber("222222222");
        account2.setBalance(BigDecimal.valueOf(2500.0));
        account2.setCustomer(customer);
        bankAccountRepository.save(account2);

        // Act
        List<BankAccount> accounts = bankAccountRepository.findByCustomerId(customer.getId());

        // Assert
        assertEquals(2, accounts.size());
        assertEquals("111111111", accounts.get(0).getAccountNumber());
        assertEquals("222222222", accounts.get(1).getAccountNumber());
    }

    @Test
    void testFindByAccountNumberNotFound() {
        // Act
        Optional<BankAccount> foundAccount = bankAccountRepository.findByAccountNumber("non_existent_account");

        // Assert
        assertFalse(foundAccount.isPresent());
    }
}

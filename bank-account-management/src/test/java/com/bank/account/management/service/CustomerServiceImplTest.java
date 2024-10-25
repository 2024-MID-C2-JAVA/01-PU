package com.bank.account.management.service;

import com.bank.account.management.controller.exception.CustomerException;
import com.bank.account.management.mapper.BankAccountMapper;
import com.bank.account.management.mapper.CustomerMapper;
import com.bank.account.management.model.Customer;
import com.bank.account.management.model.dto.CustomerDTO;
import com.bank.account.management.repository.CustomerRepository;
import com.bank.account.management.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountMapper bankAccountMapper;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers_shouldReturnListOfCustomers() {
        // Arrange
        Customer customer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername("testuser");

        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        // Act
        List<CustomerDTO> result = customerService.getAllCustomers();

        // Assert
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    @Test
    void getCustomerById_shouldReturnCustomerDTO_whenCustomerExists() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername("testuser");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        // Act
        CustomerDTO result = customerService.getCustomerById(customerId);

        // Assert
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void getCustomerById_shouldThrowException_whenCustomerDoesNotExist() {
        // Arrange
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerException.CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });
    }

    @Test
    void createCustomer_shouldReturnCustomerDTO_whenCustomerIsCreated() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername("newuser");
        Customer customer = new Customer();

        when(customerRepository.findByUsername(customerDTO.getUsername())).thenReturn(Optional.empty());
        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        // Act
        CustomerDTO result = customerService.createCustomer(customerDTO);

        // Assert
        assertEquals("newuser", result.getUsername());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void createCustomer_shouldThrowException_whenCustomerAlreadyExists() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername("existinguser");
        Customer existingCustomer = new Customer();

        when(customerRepository.findByUsername(customerDTO.getUsername())).thenReturn(Optional.of(existingCustomer));

        // Act & Assert
        assertThrows(CustomerException.CustomerAlreadyExistsException.class, () -> {
            customerService.createCustomer(customerDTO);
        });
    }

    @Test
    void deleteCustomer_shouldDeleteCustomer_whenCustomerExists() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void deleteCustomer_shouldThrowException_whenCustomerDoesNotExist() {
        // Arrange
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerException.CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(customerId);
        });
    }
}

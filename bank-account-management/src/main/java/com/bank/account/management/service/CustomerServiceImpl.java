package com.bank.account.management.service;

import com.bank.account.management.controller.exception.CustomerException;
import com.bank.account.management.mapper.BankAccountMapper;
import com.bank.account.management.mapper.CustomerMapper;
import com.bank.account.management.model.BankAccount;
import com.bank.account.management.model.dto.BankAccountDTO;
import com.bank.account.management.model.Customer;
import com.bank.account.management.model.dto.CustomerDTO;
import com.bank.account.management.repository.BankAccountRepository;
import com.bank.account.management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, BankAccountMapper bankAccountMapper, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountMapper = bankAccountMapper;
        this.customerMapper = customerMapper;
    }


    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException.CustomerNotFoundException(id));
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findByUsername(customerDTO.getUsername());
        if (existingCustomer.isPresent()) {
            throw new CustomerException.CustomerAlreadyExistsException(customerDTO.getUsername());
        }

        Customer customer = customerMapper.toEntity(customerDTO);

        customer = customerRepository.save(customer);

        return customerMapper.toDTO(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException.CustomerNotFoundException(id));
        customerRepository.delete(customer);
    }

    @Override
    public List<BankAccountDTO> getAllBankAccounts(Long id) {

        List<BankAccount> bankAccounts = bankAccountRepository.findByCustomerId(id);

        return bankAccounts.stream()
                .map(bankAccountMapper::toDTO)
                .collect(Collectors.toList());
    }

}

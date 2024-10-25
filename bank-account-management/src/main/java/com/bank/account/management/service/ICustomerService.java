package com.bank.account.management.service;


import com.bank.account.management.model.dto.BankAccountDTO;
import com.bank.account.management.model.dto.CustomerDTO;

import java.util.List;

public interface ICustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long id);

    List<BankAccountDTO> getAllBankAccounts(Long id);

}

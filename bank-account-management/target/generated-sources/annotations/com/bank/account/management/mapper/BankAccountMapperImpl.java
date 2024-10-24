package com.bank.account.management.mapper;

import com.bank.account.management.model.BankAccount;
import com.bank.account.management.model.Customer;
import com.bank.account.management.model.dto.BankAccountDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-24T19:34:46-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public BankAccountDTO toDTO(BankAccount bankAccount) {
        if ( bankAccount == null ) {
            return null;
        }

        BankAccountDTO bankAccountDTO = new BankAccountDTO();

        bankAccountDTO.setCustomerId( bankAccountCustomerId( bankAccount ) );
        bankAccountDTO.setId( bankAccount.getId() );
        bankAccountDTO.setAccountNumber( bankAccount.getAccountNumber() );
        bankAccountDTO.setBalance( bankAccount.getBalance() );

        return bankAccountDTO;
    }

    @Override
    public BankAccount toEntity(BankAccountDTO bankAccountDTO) {
        if ( bankAccountDTO == null ) {
            return null;
        }

        BankAccount bankAccount = new BankAccount();

        bankAccount.setId( bankAccountDTO.getId() );
        bankAccount.setBalance( bankAccountDTO.getBalance() );
        bankAccount.setAccountNumber( bankAccountDTO.getAccountNumber() );

        return bankAccount;
    }

    private Long bankAccountCustomerId(BankAccount bankAccount) {
        if ( bankAccount == null ) {
            return null;
        }
        Customer customer = bankAccount.getCustomer();
        if ( customer == null ) {
            return null;
        }
        Long id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

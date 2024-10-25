package com.bank.account.management.mapper;

import com.bank.account.management.model.BankAccount;
import com.bank.account.management.model.dto.BankAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankAccountMapper {
    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    @Mapping(source = "customer.id", target = "customerId")
    BankAccountDTO toDTO(BankAccount bankAccount);
    BankAccount toEntity(BankAccountDTO bankAccountDTO);
}

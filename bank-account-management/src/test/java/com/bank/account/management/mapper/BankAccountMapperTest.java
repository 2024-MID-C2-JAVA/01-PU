package com.bank.account.management.mapper;

import com.bank.account.management.model.BankAccount;
import com.bank.account.management.model.dto.BankAccountDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountMapperTest {

    private final BankAccountMapper mapper = BankAccountMapper.INSTANCE;

    @Test
    public void testToDTO() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setBalance(BigDecimal.valueOf(1000));
        bankAccount.setAccountNumber("1234567890");

        BankAccountDTO dto = mapper.toDTO(bankAccount);

        assertEquals(bankAccount.getId(), dto.getId());
        assertEquals(bankAccount.getBalance(), dto.getBalance());
        assertEquals(bankAccount.getAccountNumber(), dto.getAccountNumber());
    }

    @Test
    public void testToEntity() {
        BankAccountDTO dto = new BankAccountDTO(1L, null, "1234567890", BigDecimal.valueOf(1000));

        BankAccount bankAccount = mapper.toEntity(dto);

        assertEquals(dto.getId(), bankAccount.getId());
        assertEquals(dto.getBalance(), bankAccount.getBalance());
        assertEquals(dto.getAccountNumber(), bankAccount.getAccountNumber());
    }
}

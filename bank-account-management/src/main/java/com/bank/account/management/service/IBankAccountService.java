package com.bank.account.management.service;

import com.bank.account.management.model.dto.BankAccountDTO;
import com.bank.account.management.model.dto.DepositDTO;
import com.bank.account.management.model.dto.PurchaseDTO;
import com.bank.account.management.model.dto.WithdrawalDTO;

public interface IBankAccountService {
    BankAccountDTO createAccount(BankAccountDTO account);
    BankAccountDTO getAccount(Long id);
    void delete(Long id);
    void processDeposit(DepositDTO depositDTO);
    void processCardPurchase(PurchaseDTO purchaseDTO);
    void processWithdrawal(WithdrawalDTO withdrawalDTO);
}

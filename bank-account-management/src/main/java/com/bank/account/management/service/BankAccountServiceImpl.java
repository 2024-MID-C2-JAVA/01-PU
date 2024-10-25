package com.bank.account.management.service;

import com.bank.account.management.controller.exception.*;
import com.bank.account.management.mapper.BankAccountMapper;
import com.bank.account.management.model.*;
import com.bank.account.management.model.dto.BankAccountDTO;
import com.bank.account.management.model.dto.DepositDTO;
import com.bank.account.management.model.dto.PurchaseDTO;
import com.bank.account.management.model.dto.WithdrawalDTO;
import com.bank.account.management.model.enums.DepositType;
import com.bank.account.management.model.enums.PurchaseType;
import com.bank.account.management.repository.BankAccountRepository;
import com.bank.account.management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class BankAccountServiceImpl implements IBankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final BankAccountMapper bankAccountMapper;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository,
                                  CustomerRepository customerRepository,
                                  BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Override
    public BankAccountDTO createAccount(BankAccountDTO accountDTO) {

        Customer customer = customerRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new CustomerException.CustomerNotFoundException(accountDTO.getCustomerId()));

        BankAccount bankAccount = bankAccountMapper.toEntity(accountDTO);
        bankAccount.setCustomer(customer);
        bankAccount.setAccountNumber(generateAccountNumber());
        bankAccount.setBalance(BigDecimal.valueOf(0));

        bankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.toDTO(bankAccount);
    }

    @Override
    public void delete(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(BankAccountException.BankAccountNotFoundException::new);
        bankAccountRepository.delete(bankAccount);
    }

    @Override
    public void processDeposit(DepositDTO depositDTO) {
        if (depositDTO.getAmount() == null || depositDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException();
        }

        BankAccount account = bankAccountRepository.findByAccountNumber(depositDTO.getAccountNumber())
                .orElseThrow(BankAccountException.BankAccountNotFoundException::new);

        Customer customer = customerRepository.findById(depositDTO.getCustomerId())
                .orElseThrow(() -> new CustomerException.CustomerNotFoundException(depositDTO.getCustomerId()));

        if (!account.getCustomer().getId().equals(customer.getId())) {
            throw new AccountNotBelongsToCustomerException();
        }

        DepositType depositType;
        try {
            depositType = DepositType.valueOf(depositDTO.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidDepositTypeException(depositDTO.getType());
        }

        BigDecimal fee = calculateDepositFee(depositDTO.getAmount(), depositType);

        account.adjustBalance(depositDTO.getAmount().subtract(fee));
        bankAccountRepository.save(account);
    }

    public void processCardPurchase(PurchaseDTO purchaseDTO) {
        BankAccount account = bankAccountRepository.findByAccountNumber(purchaseDTO.getAccountNumber())
                .orElseThrow(BankAccountException.BankAccountNotFoundException::new);

        PurchaseType purchaseType;
        try {
            purchaseType = PurchaseType.valueOf(purchaseDTO.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPurchaseTypeException(purchaseDTO.getType());
        }

        BigDecimal amount = purchaseDTO.getAmount();
        BigDecimal fee = calculatePurchaseFee(purchaseType);
        BigDecimal totalCharge = amount.add(fee);

        if (account.getBalance().compareTo(totalCharge) < 0) {
            throw new InsufficientFundsException();
        }

        account.adjustBalance(totalCharge.negate());
        bankAccountRepository.save(account);
    }

    @Override
    public void processWithdrawal(WithdrawalDTO withdrawalDTO) {
        if (withdrawalDTO.getAmount() == null || withdrawalDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException();
        }

        BankAccount account = bankAccountRepository.findByAccountNumber(withdrawalDTO.getAccountNumber())
                .orElseThrow(BankAccountException.BankAccountNotFoundException::new);

        Customer customer = customerRepository.findById(withdrawalDTO.getCustomerId())
                .orElseThrow(() -> new CustomerException.CustomerNotFoundException(withdrawalDTO.getCustomerId()));

        if (!account.getCustomer().getId().equals(customer.getId())) {
            throw new AccountNotBelongsToCustomerException();
        }

        BigDecimal transactionFee = new BigDecimal("1.00");
        BigDecimal totalCharge = withdrawalDTO.getAmount().add(transactionFee);

        if (account.getBalance().compareTo(totalCharge) < 0) {
            throw new InsufficientFundsException();
        }

        account.adjustBalance(totalCharge.negate());
        bankAccountRepository.save(account);
    }

    private BigDecimal calculatePurchaseFee(PurchaseType type) {
        return switch (type) {
            case PHYSICAL -> BigDecimal.ZERO; // No fee
            case ONLINE -> new BigDecimal("5.00"); // $5 USD
            default -> throw new InvalidPurchaseTypeException(type.toString());
        };
    }


    private BigDecimal calculateDepositFee(BigDecimal amount, DepositType type) {
        return switch (type) {
            case ATM -> new BigDecimal("2.00"); // 2 USD
            case OTHER_ACCOUNT -> new BigDecimal("1.50"); // 1.5 USD
            case BRANCH -> BigDecimal.ZERO;
            default -> throw new InvalidDepositTypeException(type.toString());
        };
    }


    @Override
    public BankAccountDTO getAccount(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(BankAccountException.BankAccountNotFoundException::new);
        return bankAccountMapper.toDTO(bankAccount);
    }

    private String generateAccountNumber() {

        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }


}

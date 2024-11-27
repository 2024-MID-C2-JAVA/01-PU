package co.com.sofka.sofkau.cuentaflex.services;

import co.com.sofka.sofkau.cuentaflex.models.Account;
import co.com.sofka.sofkau.cuentaflex.models.Transaction;
import co.com.sofka.sofkau.cuentaflex.models.TransactionFactory;
import co.com.sofka.sofkau.cuentaflex.models.TransactionType;
import co.com.sofka.sofkau.cuentaflex.services.exceptions.MinimumAmountNotReachedException;
import co.com.sofka.sofkau.cuentaflex.repositories.AccountRepository;
import co.com.sofka.sofkau.cuentaflex.repositories.FeesRepository;
import co.com.sofka.sofkau.cuentaflex.services.dtos.TransactionDoneResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionsService {
    private final AccountRepository accountRepository;
    private final FeesRepository feesRepository;
    private final TransactionFactory transactionFactory;

    public TransactionsService(AccountRepository accountRepository, FeesRepository feesRepository, TransactionFactory transactionFactory) {
        this.accountRepository = accountRepository;
        this.feesRepository = feesRepository;
        this.transactionFactory = transactionFactory;
    }

    public TransactionDoneResponseDto processBranchDeposit(BigDecimal amount) {
        return processTransaction(amount, TransactionType.BRANCH_DEPOSIT);
    }

    public TransactionDoneResponseDto processAtmDeposit(BigDecimal amount) {
        return processTransaction(amount, TransactionType.ATM_DEPOSIT);
    }

    public TransactionDoneResponseDto processExternalDeposit(BigDecimal amount) {
        return processTransaction(amount, TransactionType.EXTERNAL_DEPOSIT);
    }

    public TransactionDoneResponseDto processPhysicalPurchase(BigDecimal amount) {
        return processTransaction(amount.abs().negate(), TransactionType.PHYSICAL_PURCHASE);
    }

    public TransactionDoneResponseDto processOnlinePurchase(BigDecimal amount) {
        return processTransaction(amount.abs().negate(), TransactionType.ONLINE_PURCHASE);
    }

    public TransactionDoneResponseDto processAtmWithdrawal(BigDecimal amount) {
        return processTransaction(amount.abs().negate(), TransactionType.ATM_WITHDRAWAL);
    }

    private void applyTransactionToAccount(Account account, BigDecimal amount, TransactionType transactionType, String description) {
        account.transactAmount(amount);
        Transaction transaction = this.transactionFactory.createTransaction(
                transactionType,
                amount,
                description,
                account.getBalance()
        );
        account.addTransaction(transaction);
    }

    private TransactionDoneResponseDto processTransaction(BigDecimal amount, TransactionType transactionType) {
        BigDecimal fee = this.feesRepository.getFeeValueByTransactionType(transactionType);

        if (amount.compareTo(BigDecimal.ZERO) > 0 && fee.abs().compareTo(amount) > 0) {
            throw new MinimumAmountNotReachedException("The amount for this transaction should be greater than $" + fee);
        }

        String transactionDescription = this.feesRepository.getFeeDescriptionByTransactionType(transactionType);

        Account account = this.accountRepository.getAccount();
        this.applyTransactionToAccount(account, amount, transactionType, transactionDescription);

        if (fee.abs().compareTo(BigDecimal.ZERO) > 0) {
            String feeDescription = this.feesRepository.getFeeDescriptionByTransactionType(TransactionType.FEE) + " | " + transactionDescription;
            this.applyTransactionToAccount(account, fee, TransactionType.FEE, feeDescription);
        }

        accountRepository.saveAccount(account);

        return new TransactionDoneResponseDto("Transaction was successful!", account.getBalance());
    }
}

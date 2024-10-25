package co.com.sofka.banco.service;

import co.com.sofka.banco.configuration.middleware.AccountNotExistException;
import co.com.sofka.banco.configuration.middleware.AccountNotHaveBalanceException;
import co.com.sofka.banco.configuration.middleware.TypeOfBuysNotExistException;
import co.com.sofka.banco.controller.model.request.*;
import co.com.sofka.banco.model.entity.Account;
import co.com.sofka.banco.model.entity.Bank;
import co.com.sofka.banco.model.entity.BankTransaction;
import co.com.sofka.banco.model.entity.TypeTransaction;
import co.com.sofka.banco.repository.AccountRepository;
import co.com.sofka.banco.repository.BankRepository;
import co.com.sofka.banco.repository.BankTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BankTransactionServiceImp implements IBankTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(BankTransactionServiceImp.class);

    private final BankTransactionRepository repository;

    private final AccountRepository accountRepository;

    private final BankRepository bankRepository;

    @Override
    public List<BankTransaction> getAll() {
        return repository.getAll();
    }

    @Override
    @Transactional
    public BankTransaction withdrawFromATM(BankTransactionWithdrawFromATM bankTransaction) {
        Account byNumber = accountRepository.findByNumberAndPing(bankTransaction.getAccountNumber(), bankTransaction.getPin());
        if (byNumber == null) {
            throw new AccountNotExistException("La cuenta no existe");
        }

        logger.info("Cuenta encontrada: {} {} {}", byNumber,byNumber.getBalance(),bankTransaction.getAmount()+1);

        if (byNumber.getBalance()-(bankTransaction.getAmount()+1) < 0) {
            throw new AccountNotHaveBalanceException("No tiene saldo suficiente.");
        }

        byNumber.setBalance(byNumber.getBalance() - (bankTransaction.getAmount()+1));
        accountRepository.save(byNumber);

        Bank bankById = bankRepository.findById(1L);
        bankById.setBalance(bankById.getBalance()+1);
        bankRepository.save(bankById);

        BankTransaction bankTransaction1 = new BankTransaction();
        bankTransaction1.setOriginAccount(byNumber);
        bankTransaction1.setDestinationAccount(byNumber);
        bankTransaction1.setAmount(bankTransaction.getAmount());

        TypeTransaction typeTransaction = new TypeTransaction();
        typeTransaction.setId(3);

        bankTransaction1.setTypeTransaction(typeTransaction);
        bankTransaction1.setCreatedAt(new Date(System.currentTimeMillis()));
        return repository.save(bankTransaction1);
    }

    @Override
    @Transactional
    public BankTransaction buys(BankTransactionBuys item) {
        Account client = accountRepository.findByNumberAndPing(item.getAccountNumberClient(), item.getPin());
        if (client == null) {
            throw new AccountNotExistException("La cuenta del cliente no existe");
        }

        Account store = accountRepository.findByNumber(item.getAccountNumberStore());
        if (store == null) {
            throw new AccountNotExistException("La cuenta del store no existe");
        }



        if (item.getTypeBuys()==1){
            logger.info("Cuenta encontrada: {} {} {}", client,client.getBalance(),item.getAmount());

            if (client.getBalance()-(item.getAmount()) < 0) {
                throw new AccountNotHaveBalanceException("No tiene saldo suficiente.");
            }
            client.setBalance(client.getBalance() - (item.getAmount()));
            accountRepository.save(client);

            store.setBalance(store.getBalance() + item.getAmount());
            accountRepository.save(store);

        }
        else if (item.getTypeBuys()==2){

            logger.info("Cuenta encontrada: {} {} {}", client,client.getBalance(),item.getAmount());

            if (client.getBalance()-(item.getAmount()+5) < 0) {
                throw new AccountNotHaveBalanceException("No tiene saldo suficiente.");
            }

            client.setBalance(client.getBalance() - (item.getAmount()+5));
            accountRepository.save(client);

            store.setBalance(store.getBalance() + item.getAmount());
            accountRepository.save(store);

            Bank bankById = bankRepository.findById(1L);
            bankById.setBalance(bankById.getBalance()+5);
            bankRepository.save(bankById);
        }
        else{
            throw new TypeOfBuysNotExistException("El tipo de compra no existe");
        }

        BankTransaction bankTransaction1 = new BankTransaction();
        bankTransaction1.setOriginAccount(client);
        bankTransaction1.setDestinationAccount(store);
        bankTransaction1.setAmount(item.getAmount());

        TypeTransaction typeTransaction = new TypeTransaction();
        typeTransaction.setId(2);

        bankTransaction1.setTypeTransaction(typeTransaction);
        bankTransaction1.setCreatedAt(new Date(System.currentTimeMillis()));
        return repository.save(bankTransaction1);
    }

    @Override
    public BankTransaction depositSucursal(BankTransactionDepositSucursal item) {

        Account client = accountRepository.findByNumber(item.getAccountNumberClient());
        if (client == null) {
            throw new AccountNotExistException("La cuenta del store no existe");
        }

        client.setBalance(client.getBalance() + (item.getAmount()));
        accountRepository.save(client);

        BankTransaction bankTransaction1 = new BankTransaction();
        bankTransaction1.setOriginAccount(client);
        bankTransaction1.setDestinationAccount(client);
        bankTransaction1.setAmount(item.getAmount());

        TypeTransaction typeTransaction = new TypeTransaction();
        typeTransaction.setId(1);

        bankTransaction1.setTypeTransaction(typeTransaction);
        bankTransaction1.setCreatedAt(new Date(System.currentTimeMillis()));
        return repository.save(bankTransaction1);
    }

    @Override
    public BankTransaction depositCajero(BankTransactionDepositCajero item) {
        Account client = accountRepository.findByNumberAndPing(item.getAccountNumberClient(), item.getPin());
        if (client == null) {
            throw new AccountNotExistException("La cuenta del cliente no es valida");
        }

        client.setBalance(client.getBalance() + (item.getAmount()-2));
        accountRepository.save(client);

        Bank bankById = bankRepository.findById(1L);
        bankById.setBalance(bankById.getBalance()+2);
        bankRepository.save(bankById);

        BankTransaction bankTransaction1 = new BankTransaction();
        bankTransaction1.setOriginAccount(client);
        bankTransaction1.setDestinationAccount(client);
        bankTransaction1.setAmount(item.getAmount());

        TypeTransaction typeTransaction = new TypeTransaction();
        typeTransaction.setId(1);

        bankTransaction1.setTypeTransaction(typeTransaction);
        bankTransaction1.setCreatedAt(new Date(System.currentTimeMillis()));
        return repository.save(bankTransaction1);
    }

    @Override
    public BankTransaction depositTrasferencia(BankTransactionDepositTransfer item) {
        Account client = accountRepository.findByNumberAndPing(item.getAccountNumberSender(), item.getPin());
        if (client == null) {
            throw new AccountNotExistException("La cuenta del cliente no es valida.");
        }

        Account store = accountRepository.findByNumber(item.getAccountNumberReceiver());
        if (client == null) {
            throw new AccountNotExistException("La cuenta del cliente no existe.");
        }

        if (client.getBalance()-(item.getAmount()) < 0) {
            throw new AccountNotHaveBalanceException("No tiene saldo suficiente.");
        }

        client.setBalance(client.getBalance() - item.getAmount());
        accountRepository.save(client);

        store.setBalance(client.getBalance() + (item.getAmount()-1.5));
        accountRepository.save(store);

        Bank bankById = bankRepository.findById(1L);
        bankById.setBalance(bankById.getBalance()+1.5);
        bankRepository.save(bankById);

        BankTransaction bankTransaction1 = new BankTransaction();
        bankTransaction1.setOriginAccount(client);
        bankTransaction1.setDestinationAccount(store);
        bankTransaction1.setAmount(item.getAmount());

        TypeTransaction typeTransaction = new TypeTransaction();
        typeTransaction.setId(1);

        bankTransaction1.setTypeTransaction(typeTransaction);
        bankTransaction1.setCreatedAt(new Date(System.currentTimeMillis()));
        return repository.save(bankTransaction1);
    }
}

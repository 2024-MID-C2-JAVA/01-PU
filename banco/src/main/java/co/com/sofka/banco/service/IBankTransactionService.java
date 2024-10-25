package co.com.sofka.banco.service;

import co.com.sofka.banco.controller.model.request.*;
import co.com.sofka.banco.model.entity.BankTransaction;

import java.util.List;

public interface IBankTransactionService {
    List<BankTransaction> getAll();
    BankTransaction withdrawFromATM(BankTransactionWithdrawFromATM bankTransaction);

    BankTransaction buys(BankTransactionBuys item);

    BankTransaction depositSucursal(BankTransactionDepositSucursal item);

    BankTransaction depositCajero(BankTransactionDepositCajero item);

    BankTransaction depositTrasferencia(BankTransactionDepositTransfer item);
}

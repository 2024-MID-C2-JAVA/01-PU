package com.sofka.cuentaBancaria.repositoryTest;

import com.sofka.cuentaBancaria.model.TransactionHistory;
import com.sofka.cuentaBancaria.model.TypeOfTransaction;
import com.sofka.cuentaBancaria.repository.TransactionHistoryRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionRepositoryTest {

    @MockBean
    private TransactionHistoryRepositoryImpl repository;

    @Test
    public void createTest() throws SQLException {
        TransactionHistory transaction = new TransactionHistory();
        transaction.setUserId(1);
        transaction.setTypeOfTransaction(TypeOfTransaction.ATM);
        transaction.setTotal(new BigDecimal(1200));

        when(repository.getById(Mockito.anyInt())).thenReturn(transaction);

        Optional<TransactionHistory> result = Optional.ofNullable(repository.getById(transaction.getTransactionId()));
        assertThat(result).isPresent();
        assertThat(result.get().getTotal()).isEqualTo(new BigDecimal(1200));
    }
}

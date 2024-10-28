package co.com.sofka.banktransaction.repository.jpa;

import co.com.sofka.banktransaction.model.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBankTransactionRepository extends JpaRepository<BankTransaction, Long> {
}

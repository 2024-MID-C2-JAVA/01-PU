package co.com.sofka.banktransaction.repository.jpa;

import co.com.sofka.banktransaction.model.entity.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTypeTransactionRepository extends JpaRepository<TypeTransaction, Long> {
}

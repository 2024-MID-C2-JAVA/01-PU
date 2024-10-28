package co.com.sofka.banco.repository.jpa;

import co.com.sofka.banco.model.entity.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTypeTransactionRepository extends JpaRepository<TypeTransaction, Long> {
}

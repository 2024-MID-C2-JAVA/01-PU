package co.com.sofka.banktransaction.repository.jpa;

import co.com.sofka.banktransaction.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBankRepository extends JpaRepository<Bank, Long> {
}

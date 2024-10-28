package co.com.sofka.banco.repository.jpa;

import co.com.sofka.banco.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBankRepository extends JpaRepository<Bank, Long> {
}

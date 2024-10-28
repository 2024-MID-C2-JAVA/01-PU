package co.com.sofka.banktransaction.repository.jpa;

import co.com.sofka.banktransaction.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<Client, Long> {
}

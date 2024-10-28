package co.com.sofka.banco.repository.jpa;

import co.com.sofka.banco.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<Client, Long> {
}

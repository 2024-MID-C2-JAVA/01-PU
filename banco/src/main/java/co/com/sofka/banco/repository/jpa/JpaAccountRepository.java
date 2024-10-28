package co.com.sofka.banco.repository.jpa;

import co.com.sofka.banco.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long> {

    Account findByNumberAndPin(String accountNumber,String pin);

    Account findByNumber(String accountNumber);


}

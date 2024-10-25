package co.com.sofka.sofkau.cuentaflex.configuration;

import co.com.sofka.sofkau.cuentaflex.repositories.AccountRepository;
import co.com.sofka.sofkau.cuentaflex.repositories.FeesRepository;
import co.com.sofka.sofkau.cuentaflex.repositories.memory.InMemoryAccountRepository;
import co.com.sofka.sofkau.cuentaflex.repositories.memory.InMemoryFeesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoriesConfiguration {
    @Bean
    public AccountRepository getAccountRepository() {
        return new InMemoryAccountRepository();
    }

    @Bean
    public FeesRepository getFeeRepository() {
        return new InMemoryFeesRepository();
    }
}

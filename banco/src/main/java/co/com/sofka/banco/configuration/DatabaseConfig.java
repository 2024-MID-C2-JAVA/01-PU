package co.com.sofka.banco.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class DatabaseConfig {

    @Value("${hikari.url}")
    String url;

    @Value("${hikari.username}")
    String userName;

    @Value("${hikari.password}")
    String password;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.url);
        config.setUsername(this.userName);
        config.setPassword(this.password);
        config.setConnectionTestQuery("SELECT 1");
        config.setPoolName("MyHikariCP");
        return new HikariDataSource(config);
    }
}
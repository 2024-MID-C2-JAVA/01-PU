package co.com.sofka.banco;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"co.com.sofka"})
@EnableJpaRepositories("co.com.sofka")
@EntityScan("co.com.sofka")
@ComponentScan("co.com.sofka")
@Slf4j
@OpenAPIDefinition(info = @Info(title = "API CORE",version = "0.1",description = "This API exposes endpoints to manage Banco."))
public class BancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancoApplication.class, args);
	}

}

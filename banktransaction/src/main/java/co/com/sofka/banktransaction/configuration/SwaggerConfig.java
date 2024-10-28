package co.com.sofka.banktransaction.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class SwaggerConfig {

    @Bean
    public OpenAPI defineOpenApi() {

        Contact myContact = new Contact();
        myContact.setName("PABLO SORIA ACOSTA");
        myContact.setEmail("your.email@gmail.com");

        Info information = new Info()
                .title("BANCO Management System API")
                .version("1.0")
                .description("This API exposes endpoints to manage BANCO.")
                .contact(myContact);
        return new OpenAPI().info(information);
    }
}
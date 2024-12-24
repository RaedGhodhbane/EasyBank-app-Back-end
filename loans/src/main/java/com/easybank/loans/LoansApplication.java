package com.easybank.loans;

import com.easybank.loans.dto.LoansContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.eazybytes.loans.controller") })
@EnableJpaRepositories("com.eazybytes.loans.repository")
@EntityScan("com.eazybytes.loans.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDTO.class})
@OpenAPIDefinition(
        info = @Info(
                title = "Loans microservice REST API Documentation",
                description = "EasyBank Loans microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Raed Ghodhbane",
                        email = "raed@gmail.com",
                        url = "https://github.com/RaedGhodhbane"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://github.com/RaedGhodhbane"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Easy Bank Loans microservice REST API Documentation",
                url = "https://github.com/RaedGhodhbane/swagger-ui.html"
        )
)
public class LoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
    }

}

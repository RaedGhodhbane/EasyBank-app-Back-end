package com.easybank.accounts;

import com.easybank.accounts.dto.AccountsContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDTO.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice REST API Documentation",
				description = "Easy-Bank Accounts Microservice REST API Documentation",
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
				description = "Easy-Bank Accounts Microservice REST API Documentation",
				url = "https://github.com/RaedGhodhbane/swagger-ui.html"
		)

)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}

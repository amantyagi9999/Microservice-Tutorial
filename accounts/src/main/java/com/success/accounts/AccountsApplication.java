package com.success.accounts;

import com.success.accounts.dto.AccountsPropertyDto;
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
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsPropertyDto.class})
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts MS REST API Documentation",
                description = "AmanBank Accounts MS REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Aman Tyagi",
                        email = "tutor@eazybytes.com",
                        url = "https://www.amanbank.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.amanbank.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "AmanBank Accounts microservice REST API Documentation",
                url = "https://www.amanbank.com/swagger-ui.html"
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}

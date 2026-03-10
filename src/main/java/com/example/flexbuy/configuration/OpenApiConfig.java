package com.example.flexbuy.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "flexbuy apis",
        version = "1.0",
        description = "all API's for this flexbuy project",
        contact = @Contact(
            name = "Flexbuy support",
            email = "techforcetz@gmail.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://springdoc.org"
        )
    ),
    servers = {
        @Server(url="http://localhost:8080/",description = "local server development")
    }
)
public class OpenApiConfig {
    
}   

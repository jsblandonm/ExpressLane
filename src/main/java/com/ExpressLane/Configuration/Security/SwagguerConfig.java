package com.ExpressLane.Configuration.Security;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@OpenAPIDefinition(
        info = @Info(
                title = "ExpressLane API",
                version = "1.0",
                description = "API para gestionar envíos y usuarios en ExpressLane.",
                contact = @Contact(name = "Equipo de Desarrollo", email = "dev@expresslane.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development server")
        }
)
public class SwagguerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Usa el método correcto para construir el esquema de seguridad
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new io.swagger.v3.oas.models.security.SecurityScheme() // Uso correcto de SecurityScheme
                                .type(Type.HTTP) // Tipo de esquema
                                .scheme("bearer") // Esquema bearer
                                .bearerFormat("JWT"))); // Formato JWT
    }

    @Bean
    public GroupedOpenApi publicApi() {
        // Uso de GroupedOpenApi.builder() de springdoc-openapi
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}
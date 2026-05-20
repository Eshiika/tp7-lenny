package fr.ekod.cda.ja.tp7.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie API")
                        .version("1.0.0")
                        .description("API de gestion de films et réalisateurs"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public OpenApiCustomizer removeErrorResponseContent() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    if (operation.getResponses() == null) return;

                    operation.getResponses().forEach((code, response) -> {
                        if (code.equals("400") || code.equals("401") || code.equals("403") || code.equals("500")) {
                            response.setContent(new io.swagger.v3.oas.models.media.Content());

                            switch (code) {
                                case "400":
                                    response.setDescription("Requête invalide");
                                    break;
                                case "401":
                                    response.setDescription("Authentification requise");
                                    break;
                                case "403":
                                    response.setDescription("Accès refusé");
                                    break;
                                case "500":
                                    response.setDescription("Erreur interne du serveur");
                            }
                        }
                    });
                })
        );
    }

    @Bean
    public OpenApiCustomizer forceJsonForSuccessResponses() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    if (operation.getResponses() == null) return;

                    var response200 = operation.getResponses().get("200");

                    if (response200 != null && response200.getContent() != null) {
                        var jsonContent = response200.getContent().get("*/*");

                        if (jsonContent != null) {
                            response200.getContent().remove("*/*");
                            response200.getContent().addMediaType("application/json", jsonContent);
                        }
                    }
                })
        );
    }

    @Bean
    public OpenApiCustomizer removeSecurityOnLogin() {
        return openApi -> {
            var pathItem = openApi.getPaths().get("/api/auth/login");

            if (pathItem != null && pathItem.getPost() != null) {
                pathItem.getPost().setSecurity(List.of());
            }
        };
    }
}
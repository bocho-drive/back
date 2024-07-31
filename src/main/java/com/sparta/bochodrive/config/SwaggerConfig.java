package com.sparta.bochodrive.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .info(customOpenAPI())
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }


    @Bean
    public Info customOpenAPI() {
        return new Info()
                .title("Bocho-Community-Service-REST API")
                .description("보초 커뮤니티 API Swagger")
                .version("1.0.0");
    }

}




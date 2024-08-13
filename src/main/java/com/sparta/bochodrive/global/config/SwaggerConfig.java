package com.sparta.bochodrive.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Server server = new Server();
        server.setUrl("https://api.tteokip.o-r.kr");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .info(customOpenAPI())
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .addServersItem(server);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springdoc-public")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(loginEndpointCustomizer())
                .build();
    }

    @Bean
    public Info customOpenAPI() {
        return new Info()
                .title("Bocho-Community-Service-REST API")
                .description("보초 커뮤니티 API Swagger")
                .version("1.0.0");
    }


    @Bean
    public OpenApiCustomizer loginEndpointCustomizer() {
        return openApi -> {
            Operation operation = new Operation()
                    .description("내 마음대로 얍얍");
            Schema<?> schema = new ObjectSchema()
                    .addProperties("email", new StringSchema())
                    .addProperties("password", new StringSchema());
            RequestBody requestBody = new RequestBody()
                    .content(new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType().schema(schema)));
            operation.requestBody(requestBody);

            ApiResponses apiResponses = new ApiResponses();
            apiResponses.addApiResponse("200", new ApiResponse().description("OK"));
            apiResponses.addApiResponse("401", new ApiResponse().description("Unauthorized"));
            operation.responses(apiResponses);

            operation.addTagsItem("user-controller");

            openApi.getPaths().addPathItem("/signin", new PathItem().post(operation));
        };
    }
}




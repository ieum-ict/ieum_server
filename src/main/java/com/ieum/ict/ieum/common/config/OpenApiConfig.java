package com.ieum.ict.ieum.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String BEARER_AUTH = "bearerAuth";

    @Bean
    public OpenAPI ieumOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ieum API")
                        .description("이송·병원·수용 요청 및 관리자 API 문서")
                        .version("v1"))
                .components(new Components().addSecuritySchemes(BEARER_AUTH,
                        new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH));
    }

    @Bean
    public GroupedOpenApi authApi() { return group("auth", "/auth/**"); }

    @Bean
    public GroupedOpenApi userApi() { return group("user", "/users/**"); }

    @Bean
    public GroupedOpenApi transferApi() { return group("transfer", "/transfers/**"); }

    @Bean
    public GroupedOpenApi hospitalApi() { return group("hospital", "/hospitals/**"); }

    @Bean
    public GroupedOpenApi acceptanceRequestApi() { return group("acceptance-request", "/requests/**"); }

    @Bean
    public GroupedOpenApi adminApi() { return group("admin", "/admin/**"); }

    @Bean
    public GroupedOpenApi healthApi() { return group("health", "/api/health"); }

    private GroupedOpenApi group(String group, String path) {
        return GroupedOpenApi.builder().group(group).pathsToMatch(path).build();
    }
}

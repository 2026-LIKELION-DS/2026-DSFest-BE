package com.ds.dsfest.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    String bearerAuth = "BearerAuth";

    return new OpenAPI()
        .info(
            new Info().title("DSFest API").description("덕성여자대학교 축제 2026 API 문서").version("v1.0.0"))
        .components(
            new Components()
                .addSecuritySchemes(
                    bearerAuth,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")));
  }
}

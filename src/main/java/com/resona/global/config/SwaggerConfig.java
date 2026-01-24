package com.resona.global.config;

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
    // 보안 스키마 설정 (JWT 토큰 입력 가능하게)
    String jwtSchemeName = "JWT Authentication";
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

    Components components =
        new Components()
            .addSecuritySchemes(
                jwtSchemeName,
                new SecurityScheme()
                    .name(jwtSchemeName)
                    .type(SecurityScheme.Type.HTTP) // HTTP 방식
                    .scheme("bearer")
                    .bearerFormat("JWT"));

    return new OpenAPI()
        .components(components)
        .addSecurityItem(securityRequirement)
        .info(apiInfo());
  }

  private Info apiInfo() {
    return new Info()
        .title("Resona Project API") // 프로젝트 이름 수정
        .description("Resona 프로젝트 API 명세서")
        .version("1.0.0");
  }
}

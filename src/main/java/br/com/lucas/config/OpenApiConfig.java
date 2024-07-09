package br.com.lucas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
// localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("RestFul Api Example")
                .version("v1")
                .description("some description about API")
                .termsOfService("https://lksdeveloper.com.br")
                .license(
                        new License().name("Apache 2.0")
                                .url("https://lksdeveloper.com.br")
                )
        );
    }
}

package pe.edu.vallegrande.mybackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OpenApiConfig implements WebMvcConfigurer {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springboot-public")
                .pathsToMatch("/v1/api/**")
                .build();
    }

    @Bean
    public OpenAPI apiInfo(@Value("${server.url}") String serverUrl) {
        return new OpenAPI()
            .addServersItem(new Server().url(serverUrl))  // Utiliza la URL definida en application.yml
            .info(new Info()
                    .title("REST API with SQL Server Database")
                    .description("REST API with SQL Server Database")
                    .license(new License().name("Valle Grande").url("https://vallegrande.edu.pe"))
                    .version("1.0.0")
            );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Tu dominio
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}

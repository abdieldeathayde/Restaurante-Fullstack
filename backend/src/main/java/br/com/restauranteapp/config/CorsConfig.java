package br.com.restauranteapp.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${app.cors.origins:http://localhost:4200}")
    private String origins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOrigins = Arrays.stream(origins.split(","))
                .map(String::trim).filter(value -> !value.isBlank()).toArray(String[]::new);
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}

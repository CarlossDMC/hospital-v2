package org.ifsc.hospital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Pattern mais permissivo
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(false) // Credenciais precisam ser false com wildcard
                .maxAge(3600);
    }
}
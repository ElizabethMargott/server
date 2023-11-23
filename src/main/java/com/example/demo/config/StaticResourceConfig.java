package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/uploads/avatars/**") // URL bajo la que se servirán los archivos estáticos
            .addResourceLocations("classpath:/static/uploads/avatars/"); // Ubicación de los archivos en tu proyecto
    }
}

package com.hsw.birdparkmanagement.api.ui;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost",
                        "http://localhost:81",
                        "http://172.30.3.114",
                        "http://172.30.3.114:81",
                        "http://192.168.0.196/"
                        )
                .allowedMethods("*")
                .allowCredentials(true);
    }

}

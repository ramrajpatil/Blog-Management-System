package com.blog.app.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Component
//@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
        .allowedHeaders("Authorization", "Content-Type", "Accept")
        .allowCredentials(true)
        .maxAge(3600);
	}

	
	
	
}

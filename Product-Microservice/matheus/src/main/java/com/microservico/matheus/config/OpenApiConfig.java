package com.microservico.matheus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI custonOpenApi() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("product integration - API")
				.description("API documentation by Matheus Vieira"));
	}
	
}

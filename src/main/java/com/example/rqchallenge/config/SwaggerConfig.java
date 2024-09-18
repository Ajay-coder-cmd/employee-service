package com.example.rqchallenge.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("public").pathsToMatch("/**") // Adjust the paths as needed
				.build();
	}

	@Bean
	public OpenApiCustomiser customerGlobalOpenApiCustomiser() {
		return openApi -> {
			openApi.info(new Info().title("Custom API Title").description("Custom API Description").version("1.0.0"));
		};
	}
}
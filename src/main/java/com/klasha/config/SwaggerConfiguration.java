package com.klasha.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.klasha"))
                .build()
                .apiInfo(apiDetails());
    }
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Klasha RESTful API", "Restful API for Klasha Backend Engineer task", "1.0", "Free to use",
                new springfox.documentation.service.Contact("klasha", "http://localhost:8080", "hopechijuka@gmail.com"),
                "API License", "http://localhost:8080",
                Collections.emptyList());
    }
}

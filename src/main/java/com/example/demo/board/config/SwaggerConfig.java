package com.example.demo.board.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi ImageGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("image")
                .pathsToMatch("/image/**")
                .addOpenApiCustomizer(
                        openApi ->
                                openApi.setInfo(new Info()
                                        .title("Image API")
                                        .description("이미지 생성 관련 API")
                                        .version("1.0.0")
                                )) .build();
    }
}

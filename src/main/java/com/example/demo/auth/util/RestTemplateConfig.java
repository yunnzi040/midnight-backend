package com.example.demo.auth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {

    @Value("${spring.rest-template.connect-timeout:300000}")
    private int connectTimeout; // 기본값 5분 (300초)

    @Value("${spring.rest-template.read-timeout:300000}")
    private int readTimeout; // 기본값 5분 (300초)

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);  // application.yml에서 설정한 값 사용
        factory.setReadTimeout(readTimeout);        // application.yml에서 설정한 값 사용

        return new RestTemplate(factory);
    }
}

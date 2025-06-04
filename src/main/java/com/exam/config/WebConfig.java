package com.exam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // API 경로에 대해서만 CORS 허용
                .allowedOrigins("http://localhost:3000", "https://alpaca-wired-noticeably.ngrok-free.app")  // React 개발서버 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/** 요청을 uploaded-images 폴더에서 읽어오도록 설정
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploaded-images/");
    }
}
package com.example.musicat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/profile/**").addResourceLocations("file:///upload/profile");
    }

    // 윈도우용 설정
    //registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/upload/");

    // 리눅스용 설정
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:///upload/");

}

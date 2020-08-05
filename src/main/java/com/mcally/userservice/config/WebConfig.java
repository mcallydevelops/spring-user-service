package com.mcally.userservice.config;

import com.mcally.userservice.security.LoggingHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoggingHandlerInterceptor loggingHandlerInterceptor;

    public WebConfig(LoggingHandlerInterceptor loggingHandlerInterceptor) {
        this.loggingHandlerInterceptor = loggingHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingHandlerInterceptor);
    }
}

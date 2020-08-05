package com.mcally.userservice.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoggingHandlerInterceptor extends HandlerInterceptorAdapter {

    private final ObjectMapper objectMapper;

    public LoggingHandlerInterceptor() {
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            log.info("URI: " + request.getRequestURI());
            log.info("VERB: " + request.getMethod());
        } catch (Exception ex) {
            log.error("Interceptor Error", ex);
        }
        return true;
    }
}

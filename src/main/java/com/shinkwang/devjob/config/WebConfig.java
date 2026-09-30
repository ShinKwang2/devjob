package com.shinkwang.devjob.config;

import com.shinkwang.devjob.logging.MdcInterceptor;
import com.shinkwang.devjob.ratelimit.RateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MdcInterceptor())
                .addPathPatterns("/**")
                .order(Ordered.HIGHEST_PRECEDENCE);

        registry.addInterceptor(new RateLimitInterceptor())
                .addPathPatterns("/api/auth/**"); // 로그인 등 인증 경로에만
    }
}

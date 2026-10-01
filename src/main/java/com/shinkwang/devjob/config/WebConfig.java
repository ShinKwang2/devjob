package com.shinkwang.devjob.config;

import com.shinkwang.devjob.exception.ErrorResponseWriter;
import com.shinkwang.devjob.logging.MdcInterceptor;
import com.shinkwang.devjob.ratelimit.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MdcInterceptor())
                .addPathPatterns("/**")
                .order(Ordered.HIGHEST_PRECEDENCE);

        registry.addInterceptor(new RateLimitInterceptor(errorResponseWriter))
                .addPathPatterns("/api/auth/**"); // 로그인 등 인증 경로에만
    }
}

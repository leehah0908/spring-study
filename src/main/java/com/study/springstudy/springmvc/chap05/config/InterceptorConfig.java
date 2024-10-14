package com.study.springstudy.springmvc.chap05.config;

import com.study.springstudy.springmvc.interceptor.AfterLoginInterceptor;
import com.study.springstudy.springmvc.interceptor.AutoLoginInterceptor;
import com.study.springstudy.springmvc.interceptor.BoardInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 인터셉터 등록
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final AfterLoginInterceptor afterLoginInterceptor;
    private final BoardInterceptor boardInterceptor;
    private final AutoLoginInterceptor autoLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry
                .addInterceptor(afterLoginInterceptor)
                .addPathPatterns("/members/sign-in", "/members/sign-up");

        registry.addInterceptor(boardInterceptor)
                .addPathPatterns("/board/*")
                .excludePathPatterns("/board/list", "/board/detail/*"); // 인터셉터 적용X

        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**");

    }
}

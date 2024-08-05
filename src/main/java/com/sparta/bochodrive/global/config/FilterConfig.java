package com.sparta.bochodrive.global.config;

import com.sparta.bochodrive.domain.security.filter.ServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ServletFilter> loggingFilter() {
        FilterRegistrationBean<ServletFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ServletFilter());
        registrationBean.addUrlPatterns("*"); // 필터를 적용할 URL 패턴 설정

        return registrationBean;
    }
}

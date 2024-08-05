package com.sparta.bochodrive.domain.security.filter;


import jakarta.servlet.*;

import java.io.IOException;

public class ServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            // 필터 로직
            // 여기서 예외가 발생하면 catch 블록으로 이동
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            // 예외 처리 로직
            // 로그를 남기거나 특정 예외로 변환
            throw new ServletException("필터에서 발생한 예외", e);
        }
    }
}

package com.sparta.bochodrive.domain.security.filter;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "/api/v1/auth/refresh".equals(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        if (jwtUtils.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtils.getUsername(token);
        String role = jwtUtils.getRole(token);

        User user = User.builder()
                .email(username)
                .userRole(UserRole.valueOf(role))
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        //토큰에서 추출한 사용자 정보를 담은 Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
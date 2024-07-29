package com.sparta.bochodrive.domain.security.filter;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomerUserDetailsService customUserDetails;

    public JwtFilter(JwtUtils jwtUtils,CustomerUserDetailsService customUserDetails) {
        this.jwtUtils = jwtUtils;
        this.customUserDetails=customUserDetails;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 먼저 Authoriation 헤더를 찾는다.
        String authorization = request.getHeader("Authorization");

        log.info("Authorization 헤더: {}", authorization);
        //Authoriation 헤더에 "Bearer "가 있는지 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("jwt 필터 : token null");

            // doFilter를 통해서 필터 체인을 넘어간다. 다음 필터로 넘기는 것.
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        // 검증에서 문제가 없으면 "Bearer "를 분리해준다.
        String token = authorization.split(" ")[1];


        // 토큰의 유효기간을 확인한다. JwtUtil 객체에서 구현해놓은 메소드를 통해서
        if (jwtUtils.isExpired(token)) {
            log.warn("JWT 토큰이 유효하지 않습니다.");
            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }


        String username = jwtUtils.getUsername(token);
        log.info("JWT 토큰에서 추출한 사용자 이름: {}", username);
        UserRole role = jwtUtils.getRole(token);


        setAuthentication(username);

        //검증이 완료됏으니 다음 필터로 넘긴다.
        filterChain.doFilter(request, response);

    }
    public void setAuthentication(String username){
        SecurityContext context=SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String email) {
        UserDetails userDetails=customUserDetails.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
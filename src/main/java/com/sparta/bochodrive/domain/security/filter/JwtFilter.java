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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final String secretKey;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("----authorization : {}", authorization);

        if(authorization == null || !authorization.startsWith("Bearer ")){
            log.error("authorization is wrong");
            filterChain.doFilter(request, response);
            return;
        }

        // 검증에서 문제가 없으면 "Bearer "를 분리해준다.
        String token = authorization.split(" ")[1];
        if(jwtUtils.isExpired(token)){
            log.error("Token is expired");
            filterChain.doFilter(request, response);
            return ;
        }

        String username = jwtUtils.getUsername(token);
        System.out.println("username : " + username);
        UserRole role = jwtUtils.getRole(token);
        System.out.println("role : " + role );

        CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        User user = new User();
        user.setNickname(username);
        user.setId(userDetails.getUserId());
        user.setUserRole(role);


        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //검증이 완료됏으니 다음 필터로 넘긴다.
        filterChain.doFilter(request, response);

    }

}
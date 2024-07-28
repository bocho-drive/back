package com.sparta.bochodrive.domain.security.filter;

import com.sparta.bochodrive.domain.security.event.AuthenticationSuccessEvent;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        CustomUserDetails principal = (CustomUserDetails) authResult.getPrincipal();
        long userId = principal.getUserId();
        String username = principal.getUsername();
        String role = principal.getAuthorities().stream().toList().get(0).getAuthority();

        String accessToken = jwtUtils.createAccessToken(userId, username, role);
        String refreshToken = jwtUtils.createRefreshToken();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("authorization", accessToken);
        response.setHeader("refresh-token", refreshToken);

        applicationEventPublisher.publishEvent(new AuthenticationSuccessEvent(this, refreshToken, authResult));
    }

    // 로그인 실패시 호출
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }
}
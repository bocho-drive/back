package com.sparta.bochodrive.domain.security.filter;


import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.entity.ApiResponse;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j(topic = "AuthorizationFilter")
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomerUserDetailsService customerUserDetailsService;



    public JwtFilter(JwtUtils jwtUtils,CustomerUserDetailsService customerUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customerUserDetailsService=customerUserDetailsService;
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

        String accessToken = jwtUtils.getAccessTokenFromHeader(request);
        String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);

        //accessToken 유효한 경우 -> 다음 filter로
        if (StringUtils.hasText(accessToken)) {
            boolean isAccessTokenValid = jwtUtils.validateToken(accessToken);
            if (isAccessTokenValid) {
                String username = jwtUtils.getUsername(accessToken);
                setAuthentication(username);
                filterChain.doFilter(request, response);
                return;
            }
        }

        // accessToken이 유효하지 않거나 없는 경우
        if (StringUtils.hasText(refreshToken)) {
            boolean isRefreshTokenValid = jwtUtils.validateToken(refreshToken);
            if (isRefreshTokenValid) {
                //refreshToken이 유효한 경우
                String email = jwtUtils.getUsername(refreshToken);
                CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
                UserRole role = userDetails.getUserRole();
                String newAccessToken = jwtUtils.createAccessToken(email, role);

                UserModel.UserLoginResDto body=LoginFilter.generateNewAccessToken(response, userDetails, newAccessToken, role);


                ApiResponse<UserModel.UserLoginResDto> res=ApiResponse.error(ErrorCode.EXPIRED_ACCESSTOKEN.getStatus(),
                        ErrorCode.EXPIRED_ACCESSTOKEN.getMessage(),body);

                // 응답값에 body json 추가
                CommonFuntion.addJsonBodyServletResponse(response,res);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        // 모든 토큰이 유효하지 않으면 에러 응답 전송
        jwtUtils.sendErrorResponse(response, ErrorCode.EXPIRED_REFRESHTOKEN);


    }
    public void setAuthentication(String username){
        SecurityContext context=SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String email) {
        UserDetails userDetails=customerUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }



}
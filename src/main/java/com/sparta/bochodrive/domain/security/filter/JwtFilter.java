package com.sparta.bochodrive.domain.security.filter;


import com.sparta.bochodrive.domain.refreshtoken.entity.RefreshToken;
import com.sparta.bochodrive.domain.refreshtoken.repository.RefreshTokenRepository;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.entity.ApiResponse;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j(topic = "AuthorizationFilter")
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtFilter(JwtUtils jwtUtils,CustomerUserDetailsService customerUserDetailsService,RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtils = jwtUtils;
        this.customerUserDetailsService=customerUserDetailsService;
        this.refreshTokenRepository=refreshTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /** token을 발급받은 사용자인지 확인 **/

        //request에서 먼저 Authoriation 헤더를 찾는다.
        String authorization = request.getHeader("Authorization");
        log.info("Authorization 헤더: {}", authorization);


        //Authoriation 헤더에 "Bearer "가 있는지 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("jwt 필터 : token null");
            filterChain.doFilter(request, response);// doFilter를 통해서 필터 체인을 넘어간다. 다음 필터로 넘기는 것.
            return;//조건이 해당되면 메소드 종료 (필수)

        }

        //AT,RT -> GET
        String accessToken = jwtUtils.getAccessTokenFromHeader(request);
        String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);

        /** 1) AT가 유효한 경우 **/
        if (StringUtils.hasText(accessToken)) {
            boolean isAccessTokenValid = jwtUtils.validateToken(accessToken);
            if (isAccessTokenValid) {
                String username = jwtUtils.getUsername(accessToken);
                setAuthentication(username);
                filterChain.doFilter(request, response);
                return;
            }
        }




        /** 2) AT가 만료된 경우 **/
        if (StringUtils.hasText(refreshToken)) {
            boolean isRefreshTokenValid = jwtUtils.validateToken(refreshToken);
            if (isRefreshTokenValid) {

                /**RT가 local에 저장된 RT랑 다른 경우**/
                Optional<RefreshToken> refreshTokenOptional=refreshTokenRepository.findValidRefreshToken(refreshToken, LocalDateTime.now());
                if(!refreshTokenOptional.isPresent()){
                    throw new NotFoundException(ErrorCode.INVAILD_JWT);
                }

                /** RT가 유효한 경우 -> 토큰 재발급 **/
                //1. token으로부터 사용자 정보를 가져오기
                String email = jwtUtils.getUsername(refreshToken);
                CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
                UserRole role = userDetails.getUserRole();

                //2. 토큰 재발급
                String newAccessToken = jwtUtils.createAccessToken(email, role);

                //3. 재발급된 AT를 body에 담아준다.
                UserModel.UserLoginResDto body=LoginFilter.generateNewAccessToken(userDetails, newAccessToken, role);
                ApiResponse<UserModel.UserLoginResDto> res=ApiResponse.error(ErrorCode.EXPIRED_ACCESSTOKEN.getStatus(),
                        ErrorCode.EXPIRED_ACCESSTOKEN.getMessage(),body);

                //4. 응답값에 body json 추가
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
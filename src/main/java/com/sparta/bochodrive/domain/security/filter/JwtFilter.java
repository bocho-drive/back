package com.sparta.bochodrive.domain.security.filter;

import ch.qos.logback.core.util.StringUtil;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.function.CommonFuntion;
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
        String refreshToken= jwtUtils.getRefreshTokenFromCookie(request);


        if(StringUtils.hasText(accessToken)){
            boolean tokenValid = jwtUtils.validateToken(accessToken);
            if(!tokenValid){ //accessToken 유효하지 않을 때
                if(!StringUtils.hasText(refreshToken) || !jwtUtils.validateToken(refreshToken)){ //refreshToken이 없거나, 만료되었을 경우
                    CommonFuntion.addJsonBodyServletResponse( response,"쿠키가 만료되었습니다. 로그인을 다시 해주세요.");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    // refreshToken이 유효할 경우 새로운 accessToken 발급
                    String email = jwtUtils.getUsername(refreshToken);

                    generateNewAccessToken(response,email);
                    return;
                }



            }
        }


        //accessToken 유효할 때

        String username = jwtUtils.getUsername(accessToken);
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
        UserDetails userDetails=customerUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void generateNewAccessToken(HttpServletResponse response,String email) throws IOException {

        CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

        UserRole role = userDetails.getUserRole();
        String newAccessToken = jwtUtils.createAccessToken(email, role);
        UserModel.UserLoginResDto body = UserModel.UserLoginResDto.builder()
                .userId(userDetails.getUserId()) // username을 사용하여 userId 가져오기
                .userRole(role)
                .nickname(userDetails.getUser().getNickname()) // username을 사용하여 nickname 가져오기
                .accessToken(newAccessToken) // newAccessToken으로 변경
                .build();

        CommonFuntion.addJsonBodyServletResponse(response, body);

    }

}
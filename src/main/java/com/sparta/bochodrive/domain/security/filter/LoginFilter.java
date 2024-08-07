package com.sparta.bochodrive.domain.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomerUserDetailsService customerUserDetailsService;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtil, CustomerUserDetailsService customerUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtil;
        this.customerUserDetailsService = customerUserDetailsService;

        this.setFilterProcessesUrl("/signin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserModel.UserLoginReqDto requestDto = null;
        try {
            requestDto = new ObjectMapper().readValue(request.getInputStream(), UserModel.UserLoginReqDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, password, null);


        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String email = ((CustomUserDetails) authResult.getPrincipal()).getUsername();

        // 이메일 주소로 사용자 정보를 가져온다.
        CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

        UserRole userRole = userDetails.getUserRole();
        String accessToken = jwtUtils.createAccessToken(email, userRole);

        UserModel.UserLoginResDto body = UserModel.UserLoginResDto.builder()
                .userId(userDetails.getUserId())
                .userRole(userRole)
                .nickname(userDetails.getUser().getNickname())
                .accessToken(accessToken)
                .build();

        // 응답값에 body json 추가
        CommonFuntion.addJsonBodyServletResponse(response, body);
    }


    // 로그인 실패시 호출
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }
}
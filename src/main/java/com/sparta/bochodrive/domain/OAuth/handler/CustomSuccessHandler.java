package com.sparta.bochodrive.domain.OAuth.handler;

import com.sparta.bochodrive.domain.OAuth.dto.CustomOAuth2User;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.entity.ApiResponse;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final CustomerUserDetailsService customerUserDetailsService;

//    spring.security.oauth2.client.redirectURL
    @Value("{$spring.security.oauth2.client.redirectURL}")
    private String redirectURL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,  Authentication authResult) throws IOException, ServletException {

        // 1. authResult에서 이메일정보를 가져온다.
        CustomOAuth2User user = (CustomOAuth2User) authResult.getPrincipal();
        String email=user.getName();


        // 2. 이메일 주소로 사용자 정보를 가져온다.
        CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        UserRole userRole = userDetails.getUserRole();

        // 3. 사용자 정보로 토큰 발급
        String accessToken = jwtUtils.createAccessToken(email, userRole);
        String refreshToken = jwtUtils.createRefreshToken(email);


        // 4. AT을 res body에 담아준다.
        UserModel.UserLoginResDto body = UserModel.UserLoginResDto.builder()
                .userId(userDetails.getUserId())
                .userRole(userRole)
                .nickname(userDetails.getUser().getNickname())
                .accessToken(accessToken)
                .build();
        //jsonbody 형식으로 보내주기
        ApiResponse<UserModel.UserLoginResDto> res=ApiResponse.ok(HttpStatus.OK.value(), "로그인이 완료되었습니다.",body);
        CommonFuntion.addJsonBodyServletResponse(response,res);

        // 5. RT를 쿠키("refreshToken")에 세팅한다.
        LoginFilter.addRefreshTokenToCookie(response, refreshToken);

        // 6. 프론트 리다이렉트 URL을 설정해준다.
        response.sendRedirect(redirectURL);

    }



}

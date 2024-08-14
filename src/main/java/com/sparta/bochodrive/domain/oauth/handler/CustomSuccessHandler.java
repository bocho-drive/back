package com.sparta.bochodrive.domain.oauth.handler;

import com.sparta.bochodrive.domain.oauth.dto.CustomOAuth2User;
import com.sparta.bochodrive.domain.refreshtoken.RefreshService;
import com.sparta.bochodrive.domain.refreshtoken.entity.RefreshToken;
import com.sparta.bochodrive.domain.refreshtoken.repository.RefreshTokenRepository;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.filter.LoginFilter;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.entity.ApiResponse;
import com.sparta.bochodrive.global.function.CommonFuntion;
import com.sparta.bochodrive.global.function.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final RefreshService refreshService;


    //    spring.security.oauth2.client.redirectURL
    @Value("${spring.security.oauth2.client.redirectURL}")
    private String redirectURL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        // 1. authResult에서 이메일정보를 가져온다.
        CustomOAuth2User user = (CustomOAuth2User) authResult.getPrincipal();
        String email = user.getName();


        // 2. 이메일 주소로 사용자 정보를 가져온다.
        CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        UserRole userRole = userDetails.getUserRole();

        // 3. 사용자 정보로 토큰 발급
        String accessToken = jwtUtils.createAccessToken(email, userRole);
        String refreshToken = jwtUtils.createRefreshToken(email);

        // 4. RT를 local DB에 저장해준다.
        refreshService.saveRefreshToken(refreshToken, userDetails.getUser());


        // 5. RT를 쿠키("refreshToken")에 세팅한다.
        Cookie refreshTokenToCookie = CookieUtil.createRefreshTokenToCookie(refreshToken);
        response.addCookie(refreshTokenToCookie);

        // 6. 프론트 리다이렉트 URL을 설정해준다.
        response.sendRedirect(getRedirectURL(redirectURL,accessToken));

    }

    private String getRedirectURL(String targetUrl,String token) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token",token)
                .build().toUriString();
    }



}

package com.sparta.bochodrive.domain.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.entity.ApiResponse;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "Authentication Filter")
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomerUserDetailsService customerUserDetailsService;
    private static final int COOKIE_EXPIRED_TIME = (int) (JwtUtils.REFRESH_TOKEN_TIME / 1000);

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtil, CustomerUserDetailsService customerUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtil;
        this.customerUserDetailsService = customerUserDetailsService;

        this.setFilterProcessesUrl("/signin"); //filter로 들어올 url 지정
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

        // 1. 이메일 주소로 사용자 정보를 가져온다.
        CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        UserRole role = userDetails.getUserRole();

        //2. accessToken, refreshToken 생성
        String accessToken = jwtUtils.createAccessToken(email, role);
        String refreshToken = jwtUtils.createRefreshToken(email);

        //3. RT를 cookie에 담아준다.
        addRefreshTokenToCookie(response, refreshToken);

        //4. AT를 body에 담아준다.
        UserModel.UserLoginResDto body=generateNewAccessToken(userDetails,accessToken,role);
        ApiResponse<UserModel.UserLoginResDto> res=ApiResponse.ok(HttpStatus.OK.value(),"로그인이 완료되었습니다.",body);

        //5. 응답값에 body json 추가
        CommonFuntion.addJsonBodyServletResponse(response,res);



    }


    // 로그인 실패시 호출
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }

    //토큰을 쿠키에 넣는 메소드
    public static void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(COOKIE_EXPIRED_TIME);
        response.addCookie(refreshTokenCookie);
    }


    //accessToken를 resDto에 담아주는 메소드
    public static UserModel.UserLoginResDto generateNewAccessToken(
                                              CustomUserDetails userDetails,
                                              String accessToken, UserRole role) throws IOException {

        UserModel.UserLoginResDto body = UserModel.UserLoginResDto.builder()
                .userId(userDetails.getUserId()) // username을 사용하여 userId 가져오기
                .userRole(role)
                .nickname(userDetails.getUser().getNickname()) // username을 사용하여 nickname 가져오기
                .accessToken(accessToken) // newAccessToken으로 변경
                .build();
        return body;


    }
}
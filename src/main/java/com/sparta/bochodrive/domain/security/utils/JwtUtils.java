package com.sparta.bochodrive.domain.security.utils;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.entity.ApiResponse;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.function.CommonFuntion;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtils {

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

//    // 토큰 만료시간
    private static final long ACCESS_TOKEN_TIME = 1000L * 60 * 60; // 60분
    public static final long REFRESH_TOKEN_TIME = 1000L * 60 * 60 * 24 * 30; // 30일

//    private static final long ACCESS_TOKEN_TIME = 1 * 60 * 1000L; // 1분
//    private static final long REFRESH_TOKEN_TIME = 2 * 60 * 1000L; // 2분


    @Value("${spring.jwt.secret}")
    private String secretKey;
    private Key key;

     // Base64 Encode 한 SecretKey
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }



    public String getUsername(String token) {

        return getUserInfoFromToken(token).getSubject();
    }


    public UserRole getRole(String token) {
        String roleString = getUserInfoFromToken(token).get("role", String.class);
        return UserRole.valueOf(roleString);
    }


    public boolean isExpired(String token) throws IOException {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            // 다른 예외 처리
            return false;
        }
    }


    public String createAccessToken(String username, UserRole role) {

        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
        log.info("JWT 토큰 생성: {}", token);
        return token;
    }

    //refreshToken 생성
    public String createRefreshToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
        return token;

    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty.");
        }
        return false;
    }


    //accessToken을 Header로부터 가져오기
    public String getAccessTokenFromHeader(HttpServletRequest request) {
        String accessToken=request.getHeader(AUTHORIZATION_HEADER);
        if(accessToken!=null && accessToken.startsWith(BEARER_PREFIX)) {
            return accessToken.substring(BEARER_PREFIX.length());
        }
        return null;

    }

    //refreshToken을 cookie로부터 가져오기
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        if(request.getCookies()!=null) {
            for(Cookie cookie : request.getCookies()) {
                if("refreshToken".equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }


    // 토큰 검증 후 오류 메시지를 응답으로 전송하는 메서드
    public void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        CommonFuntion.addJsonBodyServletResponse(response, ApiResponse.error(errorCode.getStatus(), errorCode.getMessage()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
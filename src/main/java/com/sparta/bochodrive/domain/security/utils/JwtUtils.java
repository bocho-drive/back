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
//    private static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 60분
//    private static final long REFRESH_TOKEN_TIME = 30 * 24 * 60 * 60 * 1000L; // 30일

    private static final long ACCESS_TOKEN_TIME = 1 * 60 * 1000L; // 1분
    private static final long REFRESH_TOKEN_TIME = 2 * 60 * 1000L; // 2분


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


    public Boolean isExpired(String token) {
        return getUserInfoFromToken(token).getExpiration().before(new Date());
    }


    public String createAccessToken(String username, UserRole role) {

        String token = BEARER_PREFIX + Jwts.builder()
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

    public String createJwt(String username, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(HttpServletResponse response, String token) throws IOException {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            CommonFuntion.addJsonBodyServletResponse(response, ApiResponse.error(ErrorCode.INVAILD_JWT.getStatus(), ErrorCode.INVAILD_JWT.getMessage()));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            CommonFuntion.addJsonBodyServletResponse(response, ApiResponse.error(ErrorCode.EXPIRED_JWT.getStatus(), ErrorCode.EXPIRED_JWT.getMessage()));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            CommonFuntion.addJsonBodyServletResponse(response, ApiResponse.error(ErrorCode.UNSUPPORTED_JWT.getStatus(), ErrorCode.UNSUPPORTED_JWT.getMessage()));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            CommonFuntion.addJsonBodyServletResponse(response, ApiResponse.error(ErrorCode.EMPTY_JWT.getStatus(), ErrorCode.EMPTY_JWT.getMessage()));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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

}
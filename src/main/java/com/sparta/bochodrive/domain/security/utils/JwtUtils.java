package com.sparta.bochodrive.domain.security.utils;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
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
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분


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


    public String createAccessToken(String username, String role) {
        long expiredMs = 1000 * 60 * 15;    // 15분
        String token = BEARER_PREFIX + Jwts.builder()

                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, signatureAlgorithm)
                .compact();
        log.info("JWT 토큰 생성: {}", token);
        return token;
    }




    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
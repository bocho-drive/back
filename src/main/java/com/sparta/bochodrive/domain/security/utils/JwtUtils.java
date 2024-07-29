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

    private final SecretKey secretKey;
    private Key key;

    public JwtUtils(@Value("${spring.jwt.secret}")String secretKey) {
        this.secretKey =
                new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        return getClaims(token).getPayload().get("username", String.class);
    }

    public UserRole getRole(String token) {
        String roleString = getUserInfoFromToken(token).get("role", String.class);
        return UserRole.valueOf(roleString);
    }

    public Integer getUserId(String token) {
        return getClaims(token).getPayload().get("userId", Integer.class);
    }

    public Boolean isExpired(String token) {
        return getClaims(token).getPayload().getExpiration().before(new Date());
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    // 만료된 토큰의 claim은 exception에서 가져올 수 있다.
    public Claims getClaimsFromExpiredToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 토큰 입니다!");
        }

        return null;
    }

    public String createAccessToken(String username, String role) {
        long expiredMs = 1000 * 60 * 15;    // 15분
        return "Bearer " + Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }


    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
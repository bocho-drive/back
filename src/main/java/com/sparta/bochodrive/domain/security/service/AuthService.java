package com.sparta.bochodrive.domain.security.service;

import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.entity.UserToken;
import com.sparta.bochodrive.domain.user.repository.UserTokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserTokenRepository userTokenRepository;

    @Transactional
    public void regenerateToken(String accessToken, String refreshToken, HttpServletResponse response) {
        Claims userClaims = jwtUtils.getClaimsFromExpiredToken(accessToken);

        if(userClaims == null) {
            throw new IllegalArgumentException("만료되지 않은 토큰 입니다.");
        }
        if(jwtUtils.isExpired(refreshToken)) {
            throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
        }

        long userId = (int)userClaims.get("userId");
        String userName = userClaims.getSubject();
        String role = (String)userClaims.get("role");

        UserToken userToken = userTokenRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 토큰 입니다."));

        if(!StringUtils.equals(refreshToken, userToken.getRefreshToken())) {
            throw new IllegalArgumentException("잘못된 토큰 입니다.");
        }

        String newAccessToken = jwtUtils.createAccessToken(userId, userName, role);
        String newRefreshToken = jwtUtils.createRefreshToken();

        userToken.updateRefreshToken(newRefreshToken);

        response.setHeader("Authorization", newAccessToken);
        response.setHeader("Refresh-Token", newRefreshToken);
    }
}
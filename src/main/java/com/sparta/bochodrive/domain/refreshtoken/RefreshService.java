package com.sparta.bochodrive.domain.refreshtoken;

import com.sparta.bochodrive.domain.refreshtoken.entity.RefreshToken;
import com.sparta.bochodrive.domain.refreshtoken.repository.RefreshTokenRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RefreshService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    public void saveRefreshToken(String refreshToken, User user) {
        refreshTokenRepository.findByUserId(user.getId()).ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshTokenEntity = RefreshToken.createRefreshToken(refreshToken, user);
        refreshTokenRepository.save(refreshTokenEntity);
    }

}

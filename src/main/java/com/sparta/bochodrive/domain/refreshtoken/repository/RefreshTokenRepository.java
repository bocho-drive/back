package com.sparta.bochodrive.domain.refreshtoken.repository;

import com.sparta.bochodrive.domain.refreshtoken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    // 만료되지 않은 리프레시 토큰을 조회하는 메서드
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.refreshToken = :refreshToken AND rt.expiredAt > :now")
    Optional<RefreshToken> findValidRefreshToken(@Param("refreshToken") String refreshToken, @Param("now") LocalDateTime now);

    Optional<RefreshToken> findByUserId(Long id);

    void deleteByUserId(Long userId);

}

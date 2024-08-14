package com.sparta.bochodrive.domain.refreshtoken.entity;


import com.sparta.bochodrive.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {


    @Id
    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // 만료 시간을 1달로 설정하는 빌더 메서드
    public static RefreshToken createRefreshToken(String token, User user) {
        return RefreshToken.builder()
                .refreshToken(token)
                .user(user)
                .expiredAt(LocalDateTime.now().plus(1, ChronoUnit.MONTHS))
                .build();
    }
}

package com.sparta.bochodrive.domain.mypage.dto;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyProfileResponseDto {

    private Long id;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private UserRole userRole;

    public MyProfileResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.userRole = user.getUserRole();
    }

}

package com.sparta.bochodrive.domain.user.model;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserModel {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRegistDto {

        @NotNull
        private String email;
        @NotNull
        private String password;
        @NotNull
        private String nickname;
        @NotNull
        private UserRole userRole;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponseDto {

        private Long id;
        private String email;
        private String nickname;
        private UserRole userRole;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLoginReqDto {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLoginResDto {
        private long userId;
        private String accessToken;
        private UserRole userRole;
        private String nickname;
    }
}
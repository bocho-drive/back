package com.sparta.bochodrive.domain.user.model;

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

        private String verifyPassword;

        @NotNull
        private String nickname;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponseDto {

        private Long id;

        private String email;

        private String nickname;
    }
}
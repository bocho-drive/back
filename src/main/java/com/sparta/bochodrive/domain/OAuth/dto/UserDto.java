package com.sparta.bochodrive.domain.OAuth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String role;
    private String email;
    private String nickname;
}
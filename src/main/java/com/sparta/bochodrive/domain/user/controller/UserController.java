package com.sparta.bochodrive.domain.user.controller;

import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.domain.user.model.UserModel.UserLoginDto;
import com.sparta.bochodrive.domain.user.model.UserModel.UserRegistDto;
import com.sparta.bochodrive.domain.user.service.UserService;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public UserModel.UserResponseDto postSignUp(@RequestBody UserRegistDto userRegistDto) {
        return userService.registUser(userRegistDto);
    }

    @PostMapping("/signin")
    public ApiResponse<?> postSignIn(@RequestBody UserLoginDto userLoginDto) {
        return null;
    }

}
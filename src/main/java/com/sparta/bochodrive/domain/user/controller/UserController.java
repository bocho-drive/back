package com.sparta.bochodrive.domain.user.controller;

import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.domain.user.service.UserService;
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

    @PostMapping
    public UserModel.UserResponseDto postSignUp(@RequestBody UserModel.UserRegistDto userRegistDto) {
        return userService.registUser(userRegistDto);
    }
}
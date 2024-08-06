package com.sparta.bochodrive.domain.user.controller;

import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.domain.user.model.UserModel.UserLoginReqDto;
import com.sparta.bochodrive.domain.user.model.UserModel.UserRegistDto;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import com.sparta.bochodrive.domain.user.service.UserService;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping()
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ApiResponse postSignUp(@RequestBody UserRegistDto userRegistDto) {
        userService.registUser(userRegistDto);
        return ApiResponse.ok(HttpStatus.OK.value(), "회원가입에 성공하였습니다.");
    }

    @PostMapping("/signin")
    public ApiResponse<UserModel.UserLoginResDto> postUserSignIn(@RequestBody UserLoginReqDto userLoginDto) {
        return ApiResponse.ok(HttpStatus.OK.value(), "로그인에 성공하였습니다.", userService.login(userLoginDto));
    }

//    @PostMapping("/api/auth/signin")
//    public ApiResponse<?> postAuthSignIn(@RequestBody UserLoginDto userLoginDto) {
//        return null;
//    }

}
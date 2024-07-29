package com.sparta.bochodrive.domain.user.controller;

import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.domain.user.model.UserModel.UserLoginDto;
import com.sparta.bochodrive.domain.user.model.UserModel.UserRegistDto;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import com.sparta.bochodrive.domain.user.service.UserService;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping()
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/v1/user/signup")
    public UserModel.UserResponseDto postSignUp(@RequestBody UserRegistDto userRegistDto) {
        return userService.registUser(userRegistDto);
    }

    @PostMapping("/api/v1/user/signin")
    public String postUserSignIn(@RequestBody UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        //비번 맞는지 확인.
        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String email = user.getEmail();
        return jwtUtils.createAccessToken(email, "USER");
    }

    @PostMapping("/api/auth/signin")
    public ApiResponse<?> postAuthSignIn(@RequestBody UserLoginDto userLoginDto) {
        return null;
    }

}
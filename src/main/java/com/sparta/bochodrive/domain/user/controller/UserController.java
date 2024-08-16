package com.sparta.bochodrive.domain.user.controller;

import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.model.UserModel.UserRegistDto;
import com.sparta.bochodrive.domain.user.service.UserService;
import com.sparta.bochodrive.global.entity.ApiResponse;
import com.sparta.bochodrive.global.function.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RequestMapping()
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse postSignUp(@RequestBody UserRegistDto userRegistDto) {
        userService.registUser(userRegistDto);
        return ApiResponse.ok(HttpStatus.OK.value(), "회원가입에 성공하였습니다.");
    }

    @DeleteMapping("/api/logout")
    public ApiResponse postLogout(HttpServletResponse response, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        userService.logout(customUserDetails.getUser());

        CookieUtil.deleteRefreshCookie(response);

        return ApiResponse.ok(HttpStatus.OK.value(), "로그아웃에 성공하였습니다.");
    }

}
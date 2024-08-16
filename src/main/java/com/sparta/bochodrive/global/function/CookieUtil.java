package com.sparta.bochodrive.global.function;


import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static final String REFRESH_TOKEN = "refreshToken";
    private static final int REFRESH_COOKIE_TIME = (int) (JwtUtils.REFRESH_TOKEN_TIME / 1000); // 30일

    public static void deleteRefreshCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, "")
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }


    public static void addRefreshCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(REFRESH_COOKIE_TIME)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}

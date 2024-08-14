package com.sparta.bochodrive.global.function;


import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import jakarta.servlet.http.Cookie;

public class CookieUtil {
    private static final int REFRESH_COOKIE_TIME = (int) (JwtUtils.REFRESH_TOKEN_TIME / 1000); // 30일

    public static Cookie createCookie(String cookieName, String value, int maxAge) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie deleteCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie createRefreshTokenToCookie(String refreshToken) {
        return createCookie("refreshToken", refreshToken, REFRESH_COOKIE_TIME);
    }
}

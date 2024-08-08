package com.sparta.bochodrive.domain.OAuth.dto;

import lombok.RequiredArgsConstructor;
import com.sparta.bochodrive.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final User user;



    //get이메일
    @Override
    public String getName() {
        return user.getEmail();
    }

    // get닉네임
    public String getNickname() {
        return user.getNickname();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
package com.sparta.bochodrive.domain.OAuth.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final UserDto userDto;

    // 구글이 가지고 있는 attribute와 네이버, 카카오가 가지고 있는 attribute의 response가 서로 다르기 때문에 아래에서 getNickname을 새로 만들어 사용한다.
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return userDto.getRole();
            }
        });

        return collection;
    }


    //get이메일
    @Override
    public String getName() {
        return userDto.getEmail();
    }

    // get닉네임
    public String getNickname() {
        return userDto.getNickname();
    }
}
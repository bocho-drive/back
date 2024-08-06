package com.sparta.bochodrive.domain.OAuth.userinfo;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Builder
@Getter
@RequiredArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{

    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return ((Map)attribute.get("kakao_account")).get("email").toString();
    }

    @Override
    public String getName() {
        return (String) ((Map) attribute.get("properties")).get("nickname");
    }
}

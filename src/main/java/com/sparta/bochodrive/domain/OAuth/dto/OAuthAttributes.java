package com.sparta.bochodrive.domain.OAuth.dto;

import com.sparta.bochodrive.domain.OAuth.userinfo.GoogleUserInfo;
import com.sparta.bochodrive.domain.OAuth.userinfo.KakaoUserInfo;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

//사용자의 정보를 담는 클래스
@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes; //google로부터 받은 사용자 정보가 저장된 map
    private String nameAttributeKey; //사용자 이름을 식별 키 -> (사용자 식별 고유 키)
    private String name;
    private String email;


    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAtrributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAtrributeKey;
        this.name = name;
        this.email = email;

    }
    public static OAuthAttributes of(String registrationId,String userNameAttributeName, Map<String,Object> attributes) {

        if("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }else if("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        GoogleUserInfo userInfo=new GoogleUserInfo(attributes);
        return OAuthAttributes.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .attributes(attributes)
                .nameAtrributeKey(userNameAttributeName)
                .build();

    }
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        KakaoUserInfo userInfo = new KakaoUserInfo(attributes);
        return OAuthAttributes.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .attributes(attributes)
                .nameAtrributeKey(userNameAttributeName)
                .build();
    }

    //OAuth 객체를 user entity로 저장
    public User toEntity(){
        return User.builder()
                .nickname(name)
                .email(email)
                .userRole(UserRole.USER)
                .build();
    }
}

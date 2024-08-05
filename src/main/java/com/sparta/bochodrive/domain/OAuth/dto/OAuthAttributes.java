package com.sparta.bochodrive.domain.OAuth.dto;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

//사용자의 정보를 담는 클래스
@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes; //google로부터 받은 사용자 정보가 저장된 map
    private String nameAttributeKey;
    private String name;
    private String email;


    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAtrributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAtrributeKey;
        this.name = name;
        this.email = email;

    }
    public static OAuthAttributes of(String registrationId,String userNameAtrributeName, Map<String,Object> attributes) {
        return ofGoogle(userNameAtrributeName,attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAtrributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAtrributeKey(userNameAtrributeName)
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

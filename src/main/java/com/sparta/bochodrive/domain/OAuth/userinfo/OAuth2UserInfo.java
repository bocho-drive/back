package com.sparta.bochodrive.domain.OAuth.userinfo;

public interface OAuth2UserInfo {

    String getProviderId();

    String getProvider();  // google, facebook

    String getEmail();

    String getName();
}

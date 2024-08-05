package com.sparta.bochodrive.domain.OAuth.service;

import com.sparta.bochodrive.domain.OAuth.dto.CustomOAuth2User;
import com.sparta.bochodrive.domain.OAuth.dto.UserDto;
import com.sparta.bochodrive.domain.OAuth.userinfo.GoogleUserInfo;
import com.sparta.bochodrive.domain.OAuth.userinfo.KakaoUserInfo;
import com.sparta.bochodrive.domain.OAuth.userinfo.OAuth2UserInfo;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2Response = null;
        if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoUserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleUserInfo(oAuth2User.getAttributes());
        } else {

            return null;
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String nickname = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        Optional<User> existData = userRepository.findByNickname(nickname);

        if (existData == null) {

            User user = new User();
            user.setNickname(nickname);
            user.setEmail(oAuth2Response.getEmail());
            user.setUserRole(UserRole.USER);

            userRepository.save(user);

            UserDto userDto = new UserDto();
            userDto.setEmail(oAuth2Response.getEmail());
            userDto.setNickname(nickname);
            userDto.setRole("ROLE_USER");

            return new CustomOAuth2User(userDto);

        } else {

            User existDataIsTrue = existData.get();
            existDataIsTrue.setEmail(oAuth2Response.getEmail());
            existDataIsTrue.setNickname(oAuth2Response.getName());

            userRepository.save(existDataIsTrue);

            UserDto userDto = new UserDto();
            userDto.setEmail(oAuth2Response.getEmail());
            userDto.setNickname(oAuth2Response.getName());
            userDto.setRole(existDataIsTrue.getUserRole().name());

            return new CustomOAuth2User(userDto);


        }



    }
}
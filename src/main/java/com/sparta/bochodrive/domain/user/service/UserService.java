package com.sparta.bochodrive.domain.user.service;

import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final JwtUtils jwtUtils;

    public UserModel.UserResponseDto registUser(UserModel.UserRegistDto userRegistDto) {
        if(userRepository.findByEmail(userRegistDto.getEmail()).isPresent()) {
            // Exception Handling 정해지면 뭐 수정
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }

        if(userRepository.findByNickname(userRegistDto.getNickname()).isPresent()) {
            //이것도
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        }

//        if(!StringUtils.equals(userRegistDto.getPassword(), userRegistDto.getVerifyPassword())) {
//            //이것도
//            throw new IllegalArgumentException("입력된 비밀번호가 일치하지 않습니다.");
//        }

        return userRepository.save(User.of(userRegistDto, passwordEncoder)).toDto();
    }


}
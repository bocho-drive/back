package com.sparta.bochodrive.domain.user.service;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.teacher.service.TeacherService;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final TeacherService teacherService;
    private final JwtUtils jwtUtils;

    /** 회원가입 */
    public UserModel.UserResponseDto registUser(UserModel.UserRegistDto userRegistDto) {
        if(userRepository.findByEmail(userRegistDto.getEmail()).isPresent()) {
            // Exception Handling 정해지면 뭐 수정
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }

        if(userRepository.findByNickname(userRegistDto.getNickname()).isPresent()) {
            //이것도
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        }

        String password = userRegistDto.getPassword();
        if(password.length() < 8 || password.length() > 20) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 20자 이하로 입력해주세요.");
        }

        // 회원가입
        UserModel.UserResponseDto result = userRepository.save(User.of(userRegistDto, passwordEncoder)).toDto();

        // 강사 회원가입
        if(userRegistDto.getUserRole().equals(UserRole.TEACHER)) {
            teacherService.registTeacher(result.getId());
        }

        return result;
    }

    /** 로그인 */
//    public UserModel.UserLoginResDto login(UserModel.UserLoginReqDto userLoginDto) {
//        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(
//                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
//        );
//
//        //비번 맞는지 확인.
//        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//
//        UserModel.UserLoginResDto res = UserModel.UserLoginResDto.builder()
////                                                                   .accessToken(jwtUtils.createAccessToken(user.getEmail(), user.getUserRole().toString()))
//                                                                   .userId(user.getId())
//                                                                   .userRole(user.getUserRole())
//                                                                   .nickname(user.getNickname())
//                                                                   .build();
//
//        return res;
//    }


}
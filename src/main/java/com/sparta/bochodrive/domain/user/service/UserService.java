package com.sparta.bochodrive.domain.user.service;

import com.sparta.bochodrive.domain.refreshtoken.entity.RefreshToken;
import com.sparta.bochodrive.domain.refreshtoken.repository.RefreshTokenRepository;
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
    private final TeacherService teacherService;
    private final RefreshTokenRepository refreshTokenRepository;

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

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
    }

    public void logout(User user){
        RefreshToken refreshToken=refreshTokenRepository.findByUserId(user.getId());
        refreshTokenRepository.delete(refreshToken);
    }


}
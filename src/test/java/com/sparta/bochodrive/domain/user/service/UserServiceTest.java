package com.sparta.bochodrive.domain.user.service;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.user.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void registUser() {
        UserModel.UserRegistDto userRegistDto = new UserModel.UserRegistDto("testUser1@gmail.com", "password", "nickname12", UserRole.TEACHER);
        UserModel.UserResponseDto result = userService.registUser(userRegistDto);

        assertEquals(userRegistDto.getEmail(), result.getEmail());
        assertEquals(userRegistDto.getNickname(), result.getNickname());
        assertEquals(userRegistDto.getUserRole(), result.getUserRole());
    }
}
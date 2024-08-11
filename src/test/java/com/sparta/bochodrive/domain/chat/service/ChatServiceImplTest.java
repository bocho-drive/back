//package com.sparta.bochodrive.domain.chat.service;
//
//import com.nimbusds.jose.shaded.gson.JsonObject;
//import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
//import com.sparta.bochodrive.domain.security.utils.JwtUtils;
//import io.jsonwebtoken.Claims;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class ChatServiceImplTest {
//
//    @Autowired
//    private ChatServiceImpl chatServiceImpl;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//
//    @Test
//    void getAccessKey() {
////        CustomUserDetails userDetails =
//        String accessKey = chatServiceImpl.getAccessKey(11L, 11L);
//
//        assertNotNull(accessKey);
//
//        Claims userInfoFromToken = jwtUtils.getUserInfoFromToken(accessKey);
//        Long userId = Long.valueOf((Integer) userInfoFromToken.get("userId"));
//        Long applyId = Long.valueOf((Integer) userInfoFromToken.get("applyId"));
//
//        assertEquals(11L, (Long) applyId);
//        assertEquals(11L, (Long) userId);
//
//
//    }
//}
package com.sparta.bochodrive.domain.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.bochodrive.domain.chat.dto.ChatResponseDto;
import com.sparta.bochodrive.domain.chat.service.ChatService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
@MessageMapping
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
    private final JwtUtils jwtUtils;
    private final UserService userService;



    //!!! SecurityContextHolder를 사용했을 때는, context값이 null이 되었음 -> 질문사항
    /** 웹소켓 세션에서 사용자 정보를 가져오는 메서드 */
    public CustomUserDetails getUserDetails(WebSocketSession session) {
        Principal principal = session.getPrincipal();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
            return (CustomUserDetails) token.getPrincipal();
        }
        return null;
    }

    /**
     * 웹소켓 연결이 성립된 후 호출되는 메서드
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            String roomId = getRoomId(session);

            chatRooms.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);
//            log.info("{} 사용자가 방 {}에 접속했습니다.", session, roomId);
            log.info("소켓 연결 성공!");
        } catch (Exception e) {
            log.error("연결을 설정하는 동안 오류가 발생했습니다: {}", e.getMessage(), e);
        }
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> dataMap = objectMapper.readValue(message.getPayload(), Map.class);

            String applyId = getRoomId(session);
            String rcvMsg = (String) dataMap.get("message");

            //TODO approvalKey검증 로직 필요
            String approvalKey = (String) dataMap.get("approvalKey");


            Claims userInfoFromToken = jwtUtils.getUserInfoFromToken(approvalKey);
            Long userId = Long.valueOf((Integer) userInfoFromToken.get("userId"));
            User user = userService.findById(userId);

            ChatResponseDto newChat = chatService.sendMessage(Long.valueOf(applyId), rcvMsg, user);

//            Map<String, String> response = new HashMap<>();
//            response.put("applyId", applyId);
//            response.put("sender", user.getNickname());
//            response.put("message", rcvMsg);

            broadcastMessage(applyId, newChat);
        } catch (Exception e) {
            log.error("메시지를 처리하는 동안 오류가 발생했습니다: {}", e.getMessage(), e);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            String roomId = getRoomId(session);
            chatRooms.getOrDefault(roomId, new HashSet<>()).remove(session);
            log.info("{} 사용자가 방 {}에서 연결이 종료되었습니다.", session, roomId);
        } catch (Exception e) {
            log.error("연결을 종료하는 동안 오류가 발생했습니다: {}", e.getMessage(), e);
        }
    }

    private String getRoomId(WebSocketSession session) {
        return session.getUri().getPath().split("/")[4];
    }

    private void broadcastMessage(String applyId, ChatResponseDto chatResponse) {
        Set<WebSocketSession> sessions = chatRooms.get(applyId);
        if (sessions != null) {
            sessions.forEach(session -> {
                try {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatResponse)));
                } catch (IOException e) {
                    log.error("메시지를 전송하는 동안 오류가 발생했습니다: {}", e.getMessage(), e);
                }
            });
        }
    }
}

package com.sparta.bochodrive.domain.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.bochodrive.domain.chat.service.ChatService;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.service.DriveMatchingApplyService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private final DriveMatchingApplyService driveMatchingApplyService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            String roomId = getRoomId(session);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
            driveMatchingApplyService.validPermission(Long.parseLong(roomId), details.getUser());

            chatRooms.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);
            log.info("{} 사용자가 방 {}에 접속했습니다.", session, roomId);
        } catch (Exception e) {
            log.error("연결을 설정하는 동안 오류가 발생했습니다: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String msg = message.getPayload();
            String roomId = getRoomId(session);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
            driveMatchingApplyService.validPermission(Long.parseLong(roomId), details.getUser());

            chatService.sendMessage(Long.parseLong(roomId), msg, details);

            Map<String, String> response = new HashMap<>();
            response.put("roomId", roomId);
            response.put("sender", details.getUsername());
            response.put("message", msg);

            broadcastMessage(roomId, response);
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

    private void broadcastMessage(String roomId, Map<String, String> message) {
        Set<WebSocketSession> sessions = chatRooms.get(roomId);
        if (sessions != null) {
            sessions.forEach(session -> {
                try {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                } catch (IOException e) {
                    log.error("메시지를 전송하는 동안 오류가 발생했습니다: {}", e.getMessage(), e);
                }
            });
        }
    }
}

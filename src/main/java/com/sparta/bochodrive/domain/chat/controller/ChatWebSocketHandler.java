package com.sparta.bochodrive.domain.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.bochodrive.domain.chat.service.ChatService;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.service.DriveMatchingApplyService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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
    private final DriveMatchingApplyService driveMatchingApplyService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();


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
            CustomUserDetails userDetails = getUserDetails(session);

            driveMatchingApplyService.validPermission(Long.parseLong(roomId), userDetails.getUser());

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
            CustomUserDetails userDetails = getUserDetails(session);

            driveMatchingApplyService.validPermission(Long.parseLong(roomId), userDetails.getUser());

            chatService.sendMessage(Long.parseLong(roomId), msg, userDetails);

            Map<String, String> response = new HashMap<>();
            response.put("roomId", roomId);
            response.put("sender", userDetails.getUsername());
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

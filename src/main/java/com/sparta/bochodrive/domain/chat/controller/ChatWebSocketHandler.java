package com.sparta.bochodrive.domain.chat.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.bochodrive.domain.chat.service.ChatService;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.service.DriveMatchingApplyService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
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

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RequiredArgsConstructor
@Service
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private final DriveMatchingApplyService driveMatchingApplyService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String roomId = session.getUri().getPath().split("/")[4];
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) userDetails.getDetails();
        driveMatchingApplyService.validPermission(Long.parseLong(roomId), details.getUser());
        Set<WebSocketSession> roomSessions = chatRooms.getOrDefault(roomId, new HashSet<>());

        roomSessions.add(session);
        chatRooms.put(roomId, roomSessions);

        log.info(session + "의 클라이언트 접속");
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        String roomId = session.getUri().getPath().split("/")[4];
        Set<WebSocketSession> roomSessions = chatRooms.get(roomId);
        if (roomSessions == null) {
            throw new IllegalArgumentException("Room not found");
        }
        DriveMatchingApply roomById = chatService.findRoomById(Long.parseLong(roomId));
        if (roomById != null) {
            Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
            //ㅌㅔㅅㅡㅌㅡㅇㅛㅇ
            //CustomUserDetails details = (CustomUserDetails) session.getAttributes().get("user");
            CustomUserDetails details = (CustomUserDetails) userDetails.getDetails();
            driveMatchingApplyService.validPermission(Long.parseLong(roomId), details.getUser());
            chatService.sendMessage(Long.parseLong(roomId), msg, details);

            Map<String, String> response = new HashMap<>();
            response.put("roomId", roomId);
            response.put("sender", Thread.currentThread().getName());
            //response.put("sender", String.valueOf(details.getUserId()));
            response.put("message", msg);
            roomSessions.forEach(s -> {
                try {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String roomId = session.getUri().getPath().split("/")[4];
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) userDetails.getDetails();
        driveMatchingApplyService.validPermission(Long.parseLong(roomId), details.getUser());
        Set<WebSocketSession> roomSessions = chatRooms.getOrDefault(roomId, new HashSet<>());
        roomSessions.remove(session);
        log.info(session + "의 클라이언트 종료");
    }
}

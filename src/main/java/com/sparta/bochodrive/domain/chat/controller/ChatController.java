package com.sparta.bochodrive.domain.chat.controller;

import com.sparta.bochodrive.domain.chat.dto.ChatResponse;
import com.sparta.bochodrive.domain.chat.service.ChatService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("{roomId}")
    public ApiResponse getMyChatRoom(@PathVariable("roomId") Long roomId,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponse.ok(HttpStatus.OK.value(), "채팅방 리스트", chatService.getChattingList(roomId, userDetails)
                .stream()
                .map(ChatResponse::from)
                .toList());
    }
}

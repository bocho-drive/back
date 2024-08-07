package com.sparta.bochodrive.domain.chat.dto;

import com.sparta.bochodrive.domain.chat.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class ChatResponse {
    private Long userId;
    private String nickname;
    private String message;
    private LocalDateTime createdAt;

    public static ChatResponse from(Chat chat) {
        return new ChatResponse(chat.getUser().getId(), chat.getUser().getNickname(), chat.getMessage(), chat.getCreatedAt());
    }
}
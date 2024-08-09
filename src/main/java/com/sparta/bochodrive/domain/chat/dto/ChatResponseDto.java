package com.sparta.bochodrive.domain.chat.dto;

import com.sparta.bochodrive.domain.chat.entity.Chat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Getter
public class ChatResponseDto {
    private final Long id;
    private final Long userId;
    private final String sender;
    private final String message;
    private final String createdAt;

    public ChatResponseDto(Chat chat) {
        this.id = chat.getId();
        this.userId = chat.getUser().getId();
        this.sender = chat.getUser().getNickname();
        this.message = chat.getMessage();
        this.createdAt = chat.getCreatedAt().toString();
    }

}
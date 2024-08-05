package com.sparta.bochodrive.domain.comment.dto;


import com.sparta.bochodrive.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment savedComment) {
        this.id = savedComment.getId();
        this.userId = savedComment.getUser().getId();
        this.author=savedComment.getUser().getNickname();
        this.content = savedComment.getContent();
        this.createdAt = savedComment.getCreatedAt();
    }
}

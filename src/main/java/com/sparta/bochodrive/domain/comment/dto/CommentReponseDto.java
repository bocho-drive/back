package com.sparta.bochodrive.domain.comment.dto;


import com.sparta.bochodrive.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public CommentReponseDto(Comment savedComment) {
        this.id = savedComment.getId();
        this.content = savedComment.getContent();
        this.createdAt = savedComment.getCreatedAt();
    }
}

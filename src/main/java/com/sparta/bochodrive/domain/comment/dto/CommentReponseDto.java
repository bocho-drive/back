package com.sparta.bochodrive.domain.comment.dto;


import com.sparta.bochodrive.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReponseDto {
    private Long id;
    private String content;

    public CommentReponseDto(Comment savedComment) {
        this.id = savedComment.getId();
        this.content = savedComment.getContent();
    }
}

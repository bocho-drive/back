package com.sparta.bochodrive.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {


    private Long communitiesId;
    private Long userId;
    private String content;
}

package com.sparta.bochodrive.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CommentRequestDto {


    private Long communityId;
    private Long userId;
    private String content;
}

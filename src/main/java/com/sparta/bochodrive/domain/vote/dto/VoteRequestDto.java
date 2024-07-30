package com.sparta.bochodrive.domain.vote.dto;

import lombok.Getter;

@Getter
public class VoteRequestDto {

    private Long userId;
    private Long communityId;
    private boolean agreeYn;
}

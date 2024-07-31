package com.sparta.bochodrive.domain.vote.dto;

import lombok.Getter;

@Getter
public class VoteRequestDto {
    
    private Long communityId;
    private boolean agreeYn;
}

package com.sparta.bochodrive.domain.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequestDto {

    private Long userId;
    private Long communityId;
    private boolean agreeYn;

}

package com.sparta.bochodrive.domain.vote.dto;

import com.sparta.bochodrive.domain.vote.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDto {
    private Long userId;
    private boolean agreeYn;

    public VoteResponseDto(Vote vote) {
        this.userId = vote.getUser().getId();
        this.agreeYn = vote.isAgreeYn();
    }
}

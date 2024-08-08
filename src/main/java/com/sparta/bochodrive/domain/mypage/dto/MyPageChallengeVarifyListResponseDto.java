package com.sparta.bochodrive.domain.mypage.dto;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;

public class MyPageChallengeVarifyListResponseDto extends CommunityListResponseDto {

    private Long challengeId;

    public MyPageChallengeVarifyListResponseDto(ChallengeVarify challengeVarify) {
        super(challengeVarify);
        this.challengeId = challengeVarify.getChallenge().getId();
    }
}


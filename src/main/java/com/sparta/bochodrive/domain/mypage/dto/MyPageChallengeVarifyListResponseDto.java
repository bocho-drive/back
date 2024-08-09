package com.sparta.bochodrive.domain.mypage.dto;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import lombok.Getter;

@Getter
public class MyPageChallengeVarifyListResponseDto extends CommunityListResponseDto {

    private final Long challengeId;
    private final Long communityId;

    public MyPageChallengeVarifyListResponseDto(ChallengeVarify challengeVarify) {
        super(challengeVarify);
        this.challengeId = challengeVarify.getChallenge().getId();
        this.communityId = challengeVarify.getCommunity().getId();
    }
}


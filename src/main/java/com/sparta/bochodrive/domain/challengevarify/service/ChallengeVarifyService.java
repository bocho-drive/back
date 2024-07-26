package com.sparta.bochodrive.domain.challengevarify.service;

import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyRequestDto;
import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyResponseDto;
import com.sparta.bochodrive.domain.user.entity.User;

public interface ChallengeVarifyService {
    public ChallengeVarifyResponseDto addChallengeVarify(ChallengeVarifyRequestDto requestDto, User user);

    public void getChallengeVarify(Long id);

    public void updateChallengeVarify(Long id, ChallengeVarifyRequestDto requestDto, User user);

    public void deleteChallengeVarify(Long id, User user);
}

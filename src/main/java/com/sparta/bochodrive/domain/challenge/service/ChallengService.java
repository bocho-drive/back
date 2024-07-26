package com.sparta.bochodrive.domain.challenge.service;


import com.sparta.bochodrive.domain.challenge.dto.ChallengeListResponseDto;
import com.sparta.bochodrive.domain.challenge.dto.ChallengeRequestDto;
import com.sparta.bochodrive.domain.challenge.dto.ChallengeResponseDto;

import java.util.List;

public interface ChallengService {
    ChallengeResponseDto addChallenge(ChallengeRequestDto requestDto);

    void updateChallenge(Long id, ChallengeRequestDto requestDto);

    void deleteChallenge(Long id);

    List<ChallengeListResponseDto> getChallengeList();

    ChallengeResponseDto getChallenge(Long id);
}

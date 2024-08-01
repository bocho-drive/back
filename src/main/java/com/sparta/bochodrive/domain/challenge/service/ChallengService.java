package com.sparta.bochodrive.domain.challenge.service;


import com.sparta.bochodrive.domain.challenge.dto.ChallengeListResponseDto;
import com.sparta.bochodrive.domain.challenge.dto.ChallengeRequestDto;
import com.sparta.bochodrive.domain.challenge.dto.ChallengeResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChallengService {
    ChallengeResponseDto addChallenge(ChallengeRequestDto requestDto);

    void updateChallenge(Long id, ChallengeRequestDto requestDto);

    void deleteChallenge(Long id);

    Page<ChallengeListResponseDto> getChallengeList(int page, int size, String sortBy, boolean isAsc);

    ChallengeResponseDto getChallenge(Long id);
}

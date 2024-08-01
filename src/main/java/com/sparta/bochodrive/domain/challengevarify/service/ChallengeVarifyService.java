package com.sparta.bochodrive.domain.challengevarify.service;

import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyRequestDto;
import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyResponseDto;
import com.sparta.bochodrive.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ChallengeVarifyService {
    Long addChallengeVarify(ChallengeVarifyRequestDto requestDto,  User user) throws IOException;

    ChallengeVarifyResponseDto getChallengeVarify(Long id);

    Long updateChallengeVarify(Long id, ChallengeVarifyRequestDto requestDto, User user) throws IOException;

    void deleteChallengeVarify(Long id, User user);

    Page<ChallengeVarifyResponseDto> getChallengeVarifies(int page, int size, String sortBy, boolean isAsc);
}

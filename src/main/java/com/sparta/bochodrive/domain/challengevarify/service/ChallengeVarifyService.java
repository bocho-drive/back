package com.sparta.bochodrive.domain.challengevarify.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface ChallengeVarifyService {
    Long addChallengeVarify(CommunityRequestDto requestDto, Long challengeId, User user) throws IOException;

    CommunityResponseDto getChallengeVarify(Long communityId, CustomUserDetails customUserDetails) throws IOException;

    Long updateChallengeVarify(Long communityId, CommunityRequestDto requestDto, User user) throws IOException;

    void deleteChallengeVarify(Long communityId, User user);

    Page<CommunityListResponseDto> getChallengeVarifies(int page, int size, String sortBy, boolean isAsc);
}

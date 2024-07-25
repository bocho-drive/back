package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;

import java.util.List;

public interface CommunityService {
    CommunityResponseDto addPost(CommunityRequestDto communityRequestDto,User user) ;
    List<CommunityListResponseDto> getAllPosts(CategoryEnum category) ;
    CommunityResponseDto getPost(Long id);
    ErrorCode updatePost(Long id, CommunityRequestDto communityRequestDto,User user);
    ErrorCode deletePost(Long id, User user) ;
}

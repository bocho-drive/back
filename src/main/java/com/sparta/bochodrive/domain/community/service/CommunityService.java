package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.UserDetailsImpl;
import org.checkerframework.checker.units.qual.C;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface CommunityService {
    CommunityResponseDto addPost(CommunityRequestDto communityRequestDto, User user);
    List<CommunityListResponseDto> getAllPosts(String category);
    CommunityResponseDto getPost(Long id);
    CommunityResponseDto updatePost(Long id, CommunityRequestDto communityRequestDto, UserDetailsImpl userDetails) throws AccessDeniedException;
    void deletePost(Long id, UserDetailsImpl userDetails) throws AccessDeniedException;
}

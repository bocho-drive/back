package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface CommunityService {
    Long addPost(CommunityRequestDto communityRequestDto,User user); ;
    Page<CommunityListResponseDto> getAllPosts(CategoryEnum category, int page, int size, String sortBy, boolean isAsc) ;
    CommunityResponseDto getPost(Long id, CustomUserDetails customUserDetails);
    Long updatePost(Long id, CommunityRequestDto communityRequestDto,User user);
    void deletePost(Long id, User user) ;
}

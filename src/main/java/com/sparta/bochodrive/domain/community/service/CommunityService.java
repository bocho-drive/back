package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.user.entity.User;
import java.util.List;

public interface CommunityService {
    CommunityResponseDto addPost(CommunityRequestDto communityRequestDto,User user) throws Exception;
    List<CommunityListResponseDto> getAllPosts(CategoryEnum category) throws Exception;
    CommunityResponseDto getPost(Long id);
    void updatePost(Long id, CommunityRequestDto communityRequestDto) throws Exception;
    void deletePost(Long id) throws Exception;
}

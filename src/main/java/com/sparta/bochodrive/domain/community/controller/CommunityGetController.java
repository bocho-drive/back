package com.sparta.bochodrive.domain.community.controller;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.service.CommunityService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communities")
@RequiredArgsConstructor
@Slf4j
public class CommunityGetController {

    private final CommunityService communityService;

    // 게시글 목록 조회
    @GetMapping
    public ApiResponse<Page<CommunityListResponseDto>> getAllPosts(
            @RequestParam(value = "category", required = false) CategoryEnum category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        Page<CommunityListResponseDto> posts = communityService.getAllPosts(category, page, size, sortBy, isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "목록 조회에 성공하였습니다.", posts);
    }


}

package com.sparta.bochodrive.domain.community.controller;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.service.CommunityServiceImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communities")
@RequiredArgsConstructor
public class CommunityGetController {

    CommunityServiceImpl communityService;

    // 게시글 목록 조회
    @GetMapping
    public ApiResponse<List<CommunityListResponseDto>> getAllPosts(@RequestParam(required = false) CategoryEnum category) {

        List<CommunityListResponseDto> posts = communityService.getAllPosts(category);
        return ApiResponse.ok(HttpStatus.OK.value(), "목록 조회에 성공하였습니다.",posts);

    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {

        CommunityResponseDto post = communityService.getPost(id);
        return ResponseEntity.ok().body(post);

    }
}

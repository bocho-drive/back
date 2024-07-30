package com.sparta.bochodrive.domain.community.controller;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.service.CommunityService;
import com.sparta.bochodrive.domain.community.service.CommunityServiceImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communities")
@RequiredArgsConstructor
@Slf4j
public class CommunityGetController {

    private final CommunityService communityService;

    // 게시글 목록 조회
    @GetMapping
    public ApiResponse<List<CommunityListResponseDto>> getAllPosts(@RequestParam(value = "category", required = false) CategoryEnum category) {

        List<CommunityListResponseDto> posts = communityService.getAllPosts(category);
        return ApiResponse.ok(HttpStatus.OK.value(), "목록 조회에 성공하였습니다.",posts);

    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<CommunityResponseDto> getPost(@PathVariable("id") Long id) {
        log.info("게시글 상세 조회 요청: id = {}", id);

        CommunityResponseDto post = communityService.getPost(id);
        log.info("게시글 상세 조회 성공: {}", post);

        return ApiResponse.ok(HttpStatus.OK.value(), "게시글 조회에 성공하였습니다.", post);
    }
}

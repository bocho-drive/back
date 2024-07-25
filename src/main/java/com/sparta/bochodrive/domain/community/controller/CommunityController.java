package com.sparta.bochodrive.domain.community.controller;


import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.service.CommunityServiceImpl;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.UserDetailsImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommunityController {

    CommunityServiceImpl communityService;

    // 게시글 작성
    @PostMapping
    public ApiResponse addPost(@RequestBody @Valid CommunityRequestDto postRequestDto,@AuthenticationPrincipal User user
    ) throws Exception {

        communityService.addPost(postRequestDto,user);
        return ApiResponse.ok(HttpStatus.OK.value(), "게시글 작성에 성공하였습니다.");

    }

    // 게시글 목록 조회
    @GetMapping
    public ApiResponse<List<CommunityListResponseDto>> getAllPosts(@RequestParam(required = false) CategoryEnum category) throws Exception{

        List<CommunityListResponseDto> posts = communityService.getAllPosts(category);
        return ApiResponse.ok(HttpStatus.OK.value(), "목록 조회에 성공하였습니다.",posts);

    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {

        CommunityResponseDto post = communityService.getPost(id);
        return ResponseEntity.ok().body(post);

    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ApiResponse updatePost(@PathVariable Long id,
                                          @RequestBody @Valid CommunityRequestDto postRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

        communityService.updatePost(id, postRequestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "수정에 성공하였습니다.");

    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ApiResponse deletePost(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{

        communityService.deletePost(id,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "삭제에 성공하였습니다.");

    }

}

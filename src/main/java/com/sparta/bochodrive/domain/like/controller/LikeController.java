package com.sparta.bochodrive.domain.like.controller;

import com.sparta.bochodrive.domain.like.dto.LikeRequestDto;
import com.sparta.bochodrive.domain.like.entity.Like;
import com.sparta.bochodrive.domain.like.repository.LikeRepository;
import com.sparta.bochodrive.domain.like.service.LikeService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    //좋아요
    @PostMapping
    public ApiResponse addLike(@RequestBody @Valid LikeRequestDto likeRequestDto,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        likeService.addLike(likeRequestDto,customUserDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "좋아요 등록에 성공하셨습니다.");

    }

    //좋아요 취소
    @DeleteMapping
    public ApiResponse deleteLike(@RequestBody @Valid LikeRequestDto likeRequestDto,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        likeService.deleteLike(likeRequestDto,customUserDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "좋아요 삭제에 성공하셨습니다.");

    }


}


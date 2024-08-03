package com.sparta.bochodrive.domain.challengevarify.controller;

import com.sparta.bochodrive.domain.challengevarify.service.ChallengeVarifyService;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/challenge_verifies")
@RequiredArgsConstructor
@Slf4j
public class ChallengeVarifyController {
    private final ChallengeVarifyService challengeVarifyService;

    //챌린지 인증 글 등록
    @PostMapping
    public ApiResponse<Long> addChallengeVarify(@ModelAttribute CommunityRequestDto requestDto
            ,@RequestParam("challengeId") Long challengeId
            ,@AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {

        // 로그 추가

        Long id=challengeVarifyService.addChallengeVarify(requestDto,challengeId,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증에 성공하셨습니다.",id);
    }

    //챌린지 인증 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<CommunityResponseDto> getChallengeVarify(@PathVariable("id") Long id,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        CommunityResponseDto challengeVarify=challengeVarifyService.getChallengeVarify(id,userDetails);
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증 조회에 성공하셨습니다.",challengeVarify);
    }

    //챌린지 목록 조회
    @GetMapping
    public ApiResponse<Page<CommunityListResponseDto>> getChallengeVarifies(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                                                              @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                              @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        Page<CommunityListResponseDto> challengeVarifies=challengeVarifyService.getChallengeVarifies(page,size,sortBy,isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증 목록 조회에 성공하였습니다.",challengeVarifies);
    }


    //챌린지 인증 수정
    @PutMapping("/{id}")
    public ApiResponse<Long> updateChallengeVarify(@PathVariable("id") Long id,
                                             @ModelAttribute CommunityRequestDto requestDto,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        Long challengeVarifyId= challengeVarifyService.updateChallengeVarify(id,requestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증 수정에 성공하셨습니다.",challengeVarifyId);
    }


    //챌린지 인증 삭제
    @DeleteMapping("/{id}")
    public ApiResponse deleteChallengeVarify(@PathVariable("id") Long id,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        challengeVarifyService.deleteChallengeVarify(id,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증 삭제에 성공하셨습니다.");
    }

}

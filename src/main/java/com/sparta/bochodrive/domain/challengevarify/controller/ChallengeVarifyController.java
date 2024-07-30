package com.sparta.bochodrive.domain.challengevarify.controller;


import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyRequestDto;
import com.sparta.bochodrive.domain.challengevarify.service.ChallengeVarifyService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenge_verifies")
@RequiredArgsConstructor
public class ChallengeVarifyController {
    private final ChallengeVarifyService challengeVarifyService;

    //챌린지 인증 글 등록
    @PostMapping
    public ApiResponse addChallengeVarify(@RequestBody @Valid ChallengeVarifyRequestDto requestDto
                                          , @AuthenticationPrincipal CustomUserDetails userDetails) {
        challengeVarifyService.addChallengeVarify(requestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증에 성공하셨습니다.");
    }

    @GetMapping("/{id}")
    public ApiResponse getChallengeVarify(@PathVariable Long id) {
        challengeVarifyService.getChallengeVarify(id);
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증 조회에 성공하셨습니다.");
    }

    @PutMapping("/{id}")
    public ApiResponse updateChallengeVarify(@PathVariable Long id,
                                             @RequestBody @Valid ChallengeVarifyRequestDto requestDto,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        challengeVarifyService.updateChallengeVarify(id,requestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증 수정에 성공하셨습니다.");
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteChallengeVarify(@PathVariable Long id,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        challengeVarifyService.deleteChallengeVarify(id,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 인증 삭제에 성공하셨습니다.");
    }

}

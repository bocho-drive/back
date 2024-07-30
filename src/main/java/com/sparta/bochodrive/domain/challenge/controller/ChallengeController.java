package com.sparta.bochodrive.domain.challenge.controller;

import com.sparta.bochodrive.domain.challenge.dto.ChallengeListResponseDto;
import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challenge.service.ChallengeServiceImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeServiceImpl challengeService;

    @GetMapping
    public ApiResponse<Page<ChallengeListResponseDto>> getChallengeList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                                        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                        @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        challengeService.getChallengeList(page,size,sortBy,isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(),"챌린지 목록 조회에 성공하셨습니다.");
    }

    @GetMapping("{id}")
    public ApiResponse getChallenge(@PathVariable("id") Long id) {
        challengeService.getChallenge(id);
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 상세 조회에 성공하셨습니다.");
    }


}

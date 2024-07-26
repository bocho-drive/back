package com.sparta.bochodrive.domain.challenge.controller;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challenge.service.ChallengeServiceImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeServiceImpl challengeService;

    @GetMapping
    public ApiResponse getChallengeList() {
        challengeService.getChallengeList();
        return ApiResponse.ok(HttpStatus.OK.value(),"챌린지 목록 조회에 성공하셨습니다.");
    }

    @GetMapping("{id}")
    public ApiResponse getChallenge(@PathVariable Long id) {
        challengeService.getChallenge(id);
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 상세 조회에 성공하셨습니다.");
    }


}

package com.sparta.bochodrive.domain.challenge.controller;

import com.sparta.bochodrive.domain.challenge.dto.ChallengeRequestDto;
import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challenge.service.ChallengeServiceImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

//챌린지 조회, 삭제, 수정
@RestController
@RequestMapping("/auth/challenges")
@RequiredArgsConstructor
public class ManageChallengeController {

    private final ChallengeServiceImpl challengeService;


    @PostMapping
    @Secured("ADMIN")
    public ApiResponse addChallenge(@RequestBody @Valid ChallengeRequestDto requestDto) {
        challengeService.addChallenge(requestDto);
        return ApiResponse.ok(HttpStatus.CREATED.value(), "챌린지 등록에 성공하셨습니다.");
    }
    @PutMapping("{id}")
    @Secured("ADMIN")
    public ApiResponse updateChallenge(@PathVariable Long id,
                                       @RequestBody @Valid ChallengeRequestDto requestDto) {
        challengeService.updateChallenge(id,requestDto);
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 수정에 성공하셨습니다.");
    }
    @DeleteMapping("{id}")
    @Secured("ADMIN")
    public ApiResponse deleteChallenge(@PathVariable Long id) {
        challengeService.deleteChallenge(id);
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 삭제에 성공하셨습니다.");
    }

}

package com.sparta.bochodrive.domain.challenge.controller;

import com.sparta.bochodrive.domain.challenge.dto.ChallengeResponseDto;
import com.sparta.bochodrive.domain.challenge.service.ChallengeServiceImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeServiceImpl challengeService;

    @GetMapping
    public ApiResponse<Page<ChallengeResponseDto>> getChallengeList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                                        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                        @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        Page<ChallengeResponseDto> challengeResponseDtos=challengeService.getChallengeList(page,size,sortBy,isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(),"챌린지 목록 조회에 성공하셨습니다.",challengeResponseDtos);
    }

    @GetMapping("{id}")
    public ApiResponse<ChallengeResponseDto> getChallenge(@PathVariable("id") Long id) {
        ChallengeResponseDto challengeResponseDto=challengeService.getChallenge(id);
        return ApiResponse.ok(HttpStatus.OK.value(), "챌린지 상세 조회에 성공하셨습니다.",challengeResponseDto);
    }


}

package com.sparta.bochodrive.domain.vote.controller;

import com.sparta.bochodrive.domain.vote.dto.VoteRequestDto;

import com.sparta.bochodrive.domain.vote.service.VoteService;
import com.sparta.bochodrive.domain.vote.service.VoteServiceImpl;
import com.sparta.bochodrive.global.UserDetailsImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteServiceImpl voteService;


    //투표 참여
    @PostMapping
    public ApiResponse participateVote(@Valid @RequestBody VoteRequestDto voteRequestDto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        voteService.participateVote(voteRequestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(),"투표에 참여하셨습니다.");
    }




    //투표 취소
    @DeleteMapping("{id}")
    public ApiResponse cancelVote(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        voteService.cancelVote(id,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(),"투표를 취소하셨습니다.");

    }
}

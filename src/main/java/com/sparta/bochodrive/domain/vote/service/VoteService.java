package com.sparta.bochodrive.domain.vote.service;


import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.vote.dto.VoteRequestDto;

public interface VoteService {
    void participateVote(VoteRequestDto voteRequestDto, User user);

    void cancelVote(Long id, User user);
}
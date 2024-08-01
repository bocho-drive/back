package com.sparta.bochodrive.domain.vote.service;


import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.vote.dto.VoteRequestDto;
import com.sparta.bochodrive.domain.vote.dto.VoteResponseDto;

import java.util.List;

public interface VoteService {
    void participateVote(VoteRequestDto voteRequestDto, User user);

    void cancelVote(Long id, User user);

    List<VoteResponseDto> getVoteInfo(Long communityId);
}
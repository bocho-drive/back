package com.sparta.bochodrive.domain.vote.service;

import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.vote.dto.VoteRequestDto;
import com.sparta.bochodrive.domain.vote.entity.Vote;
import com.sparta.bochodrive.domain.vote.repository.VoteRepository;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {

    private VoteRepository voteRepository;
    private CommonFuntion commonFuntion;


    @Override
    public void participateVote(VoteRequestDto voteRequestDto, User user) {

        commonFuntion.existsById(user.getId());
        Vote vote=new Vote(voteRequestDto);
        voteRepository.save(vote);


    }

    @Override
    public void cancelVote(Long id, User user) {
        commonFuntion.existsById(user.getId());
        Vote vote=voteRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.VOTE_NOT_FOUND));
        voteRepository.delete(vote);
    }
}


package com.sparta.bochodrive.domain.vote.service;

import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.community.service.CommunityService;
import com.sparta.bochodrive.domain.community.service.CommunityServiceImpl;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.vote.dto.VoteRequestDto;
import com.sparta.bochodrive.domain.vote.entity.Vote;
import com.sparta.bochodrive.domain.vote.repository.VoteRepository;
import com.sparta.bochodrive.global.exception.DuplicateVoteException;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final CommonFuntion commonFuntion;
    private final CommunityServiceImpl communityService;


    @Override
    public void participateVote(VoteRequestDto voteRequestDto, User user) {

        commonFuntion.existsById(user.getId());

        // 중복 투표 예외처리 로직
        Optional<Vote> existingVote = voteRepository.findByUserIdAndCommunityId(voteRequestDto.getUserId(), voteRequestDto.getCommunityId());
        if (existingVote.isPresent()) {
            throw new DuplicateVoteException(ErrorCode.VOTE_NOT_DUPLICATE);
        }

        Community community = communityService.findCommunityById(voteRequestDto.getCommunityId());

        Vote vote = new Vote(voteRequestDto, community, user);

        voteRepository.save(vote);

    }



    //투표 취소
    @Override
    public void cancelVote(Long id, User user) {
        commonFuntion.existsById(user.getId());
        Vote vote=voteRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.VOTE_NOT_FOUND));
        voteRepository.delete(vote);
    }
}

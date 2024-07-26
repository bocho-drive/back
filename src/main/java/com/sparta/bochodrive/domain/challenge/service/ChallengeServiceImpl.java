package com.sparta.bochodrive.domain.challenge.service;

import com.sparta.bochodrive.domain.challenge.dto.ChallengeListResponseDto;
import com.sparta.bochodrive.domain.challenge.dto.ChallengeRequestDto;
import com.sparta.bochodrive.domain.challenge.dto.ChallengeResponseDto;
import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challenge.repository.ChallengeRepository;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeServiceImpl implements ChallengService {
    private final ChallengeRepository challengeRepository;


    //챌린지 작성
    @Override

    public ChallengeResponseDto addChallenge(ChallengeRequestDto requestDto) {


        Challenge challenge = new Challenge(requestDto);
        Challenge savedChallenge = challengeRepository.save(challenge);
        return new ChallengeResponseDto(savedChallenge);
    }
    //챌린지 목록 조회
    @Override
    public List<ChallengeListResponseDto> getChallengeList() {
        List<Challenge> challenges = challengeRepository.findAllByOrderByCreatedAtDesc();
        return challenges.stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    //챌린지 상세 조회
    @Override
    public ChallengeResponseDto getChallenge(Long id) {
        Challenge challenge = findChallengeById(id);
        return new ChallengeResponseDto(challenge);
    }

    //챌린지 수정
    @Override

    public void updateChallenge(Long id, ChallengeRequestDto requestDto) {
        Challenge challenge = findChallengeById(id);
        challenge.update(requestDto);
        challengeRepository.save(challenge);

    }

    //챌린지 삭제
    @Override
    public void deleteChallenge(Long id) {
        Challenge challenge = findChallengeById(id);
        challengeRepository.delete(challenge);
    }



    public Challenge findChallengeById(Long id) {
        return challengeRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.CHALLENGE_NOT_FOUND));
    }
}

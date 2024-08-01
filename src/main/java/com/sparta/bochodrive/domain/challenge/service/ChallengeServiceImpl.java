package com.sparta.bochodrive.domain.challenge.service;

import com.sparta.bochodrive.domain.challenge.dto.ChallengeRequestDto;
import com.sparta.bochodrive.domain.challenge.dto.ChallengeResponseDto;
import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challenge.repository.ChallengeRepository;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public Page<ChallengeResponseDto> getChallengeList(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Challenge> challengePage;

        challengePage = challengeRepository.findAllByOrderByCreatedAtDesc(pageable);
        return challengePage.map(ChallengeResponseDto::new);
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

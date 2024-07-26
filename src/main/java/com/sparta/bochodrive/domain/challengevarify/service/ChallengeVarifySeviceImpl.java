package com.sparta.bochodrive.domain.challengevarify.service;


import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyRequestDto;
import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyResponseDto;
import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.challengevarify.repository.ChallengeVarifyRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.function.CommonFunctionFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeVarifySeviceImpl implements ChallengeVarifyService {
    private final ChallengeVarifyRepository challengeVarifyRepository;

    @Override
    public ChallengeVarifyResponseDto addChallengeVarify(ChallengeVarifyRequestDto requestDto, User user) {

        ChallengeVarify challengeVarify = new ChallengeVarify(requestDto);
        ChallengeVarify challengeVarifySaved = challengeVarifyRepository.save(challengeVarify);

        return new ChallengeVarifyResponseDto(challengeVarifySaved);
    }


    @Override
    public void getChallengeVarify(Long id) {

    }

    @Override
    public void updateChallengeVarify(Long id, ChallengeVarifyRequestDto requestDto, User user) {

    }

    @Override
    public void deleteChallengeVarify(Long id, User user) {

    }
}

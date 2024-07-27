package com.sparta.bochodrive.domain.challengevarify.service;


import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyRequestDto;
import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyResponseDto;
import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.challengevarify.repository.ChallengeVarifyRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.function.CommonFunctionFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeVarifySeviceImpl implements ChallengeVarifyService {
    private final ChallengeVarifyRepository challengeVarifyRepository;
    private final CommonFuntion commonFuntion;

    @Override
    public ChallengeVarifyResponseDto addChallengeVarify(ChallengeVarifyRequestDto requestDto, User user) {

        commonFuntion.existsById(user.getId());

        ChallengeVarify challengeVarify = new ChallengeVarify(requestDto);
        ChallengeVarify challengeVarifySaved = challengeVarifyRepository.save(challengeVarify);

        return new ChallengeVarifyResponseDto(challengeVarifySaved);
    }


    @Override
    public ChallengeVarifyResponseDto getChallengeVarify(Long id) {

        ChallengeVarify challengeVarify = findChallengeVarifyById(id);
        return new ChallengeVarifyResponseDto(challengeVarify);
    }

    @Override
    public void updateChallengeVarify(Long id, ChallengeVarifyRequestDto requestDto, User user){
        commonFuntion.existsById(user.getId());
        ChallengeVarify challengeVarify = findChallengeVarifyById(id);
        if(!challengeVarify.getCommunity().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.UPDATE_FAILED);
        }
        challengeVarify.update(requestDto);
        challengeVarifyRepository.save(challengeVarify);

    }

    @Override
    public void deleteChallengeVarify(Long id, User user) {
        commonFuntion.existsById(user.getId());
        ChallengeVarify challengeVarify = findChallengeVarifyById(id);
        if(!challengeVarify.getCommunity().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
        }
        challengeVarify.getCommunity().setDeleteYn(true);
        challengeVarifyRepository.save(challengeVarify);


    }

    public ChallengeVarify findChallengeVarifyById(Long id) {
        return challengeVarifyRepository.findById(id).orElseThrow(()-> new NotFoundException(ErrorCode.CHALLENGE_NOT_FOUND));
    }
}

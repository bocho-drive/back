package com.sparta.bochodrive.domain.challengevarify.service;


import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challenge.repository.ChallengeRepository;
import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyRequestDto;
import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyResponseDto;
import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.challengevarify.repository.ChallengeVarifyRepository;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.community.service.CommunityService;
import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
import com.sparta.bochodrive.domain.imageS3.repository.ImageS3Repository;
import com.sparta.bochodrive.domain.imageS3.service.ImageS3Service;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChallengeVarifySeviceImpl implements ChallengeVarifyService {

    private final ChallengeVarifyRepository challengeVarifyRepository;
    private final CommunityRepository communityRepository;
    private final CommonFuntion commonFuntion;
    private final ImageS3Repository imageS3Repository;
    private final ImageS3Service imageS3Service;
    private final ChallengeRepository challengeRepository;

    @Override
    public Long addChallengeVarify(ChallengeVarifyRequestDto requestDto, Long challengeId, User user) {

        // 사용자 ID가 userRepository에 있는지 확인
        commonFuntion.existsById(user.getId());

        // 게시글 객체 생성 후 저장
        Community community = new Community(requestDto, user);
        Community savedCommunity = communityRepository.save(community);

        // 챌린지 찾기
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHALLENGE_NOT_FOUND));

        // ChallengeVarify 객체 생성 후 저장
        ChallengeVarify challengeVarify = new ChallengeVarify(user, savedCommunity, challenge);
        ChallengeVarify challengeVarifySaved = challengeVarifyRepository.save(challengeVarify);

        // 이미지 업로드
        List<MultipartFile> requestImages = requestDto.getImage();
        if (requestImages != null && !requestImages.isEmpty()) {
            for (MultipartFile file : requestImages) {
                try {
                    String url = imageS3Service.upload(file);
                    String filename = imageS3Service.getFileName(url);
                    ImageS3 imageS3 = new ImageS3(url, filename, challengeVarifySaved.getCommunity());
                    imageS3Repository.save(imageS3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return challengeVarifySaved.getId();
    }


    @Override
    public ChallengeVarifyResponseDto getChallengeVarify(Long id) {

        ChallengeVarify challengeVarify = findChallengeVarifyById(id);
        Community community=challengeVarify.getCommunity();

        //deleteYn=true인지 확인하는 로직
        commonFuntion.deleteCommunity(community.getId());


        community.setViewCount(community.getViewCount()+1);
        communityRepository.save(community);
        return new ChallengeVarifyResponseDto(challengeVarify);
    }

    @Override
    public Page<ChallengeVarifyResponseDto> getChallengeVarifies(int page, int size, String sortBy, boolean isAsc) {
        // 정렬 방향 설정
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<ChallengeVarify> challengeVarifyPage = challengeVarifyRepository.findAllByCommunityDeleteYnFalseOrderByCreatedDateDesc(pageable);
        return challengeVarifyPage.map(ChallengeVarifyResponseDto::new);

    }

    @Override
    public Long updateChallengeVarify(Long id, ChallengeVarifyRequestDto requestDto, User user) {
        commonFuntion.existsById(user.getId());
        ChallengeVarify challengeVarify = findChallengeVarifyById(id);

        Community community=challengeVarify.getCommunity();
        //deleteYn=true인지 확인하는 로직
        commonFuntion.deleteCommunity(community.getId());

        if(!community.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.UPDATE_FAILED);
        }

        // 이미지 업로드
        List<MultipartFile> requestImages = requestDto.getImage();
        if (requestImages != null && !requestImages.isEmpty()) {
            for (MultipartFile file : requestImages) {
                try {
                    String url = imageS3Service.upload(file);
                    String filename = imageS3Service.getFileName(url);
                    ImageS3 imageS3 = new ImageS3(url, filename, challengeVarify.getCommunity());
                    imageS3Repository.save(imageS3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        community.update(requestDto);
        ChallengeVarify saveChallengeVarify=challengeVarifyRepository.save(challengeVarify);
        return saveChallengeVarify.getId();
    }

    @Override
    public void deleteChallengeVarify(Long id, User user) {
        commonFuntion.existsById(user.getId());
        ChallengeVarify challengeVarify = findChallengeVarifyById(id);

        //deleteYn=true인지 확인하는 로직
        commonFuntion.deleteCommunity(challengeVarify.getCommunity().getId());


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

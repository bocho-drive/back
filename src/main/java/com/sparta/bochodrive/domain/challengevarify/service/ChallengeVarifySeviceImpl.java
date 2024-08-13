package com.sparta.bochodrive.domain.challengevarify.service;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challenge.repository.ChallengeRepository;
import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.challengevarify.repository.ChallengeVarifyRepository;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
import com.sparta.bochodrive.domain.imageS3.repository.ImageS3Repository;
import com.sparta.bochodrive.domain.imageS3.service.ImageS3Service;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Long addChallengeVarify(CommunityRequestDto requestDto, Long challengeId, User user) {


        // 게시글 객체 생성 후 저장
        Community community = Community.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .category(requestDto.getCategory())
                .user(user)
                .build();

        Community savedCommunity = communityRepository.save(community);

        // 챌린지 찾기
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHALLENGE_NOT_FOUND));

        // ChallengeVarify 객체 생성 후 저장
        ChallengeVarify challengeVarify = ChallengeVarify.builder()
                .user(user)
                .community(savedCommunity)
                .challenge(challenge)
                .build();

        ChallengeVarify challengeVarifySaved = challengeVarifyRepository.save(challengeVarify);

        // 이미지 업로드
        List<MultipartFile> requestImages = requestDto.getImage();
        if (requestImages != null && !requestImages.isEmpty()) {
            for (MultipartFile file : requestImages) {
                try {
                    String url = imageS3Service.upload(file);
                    String filename = imageS3Service.getFileName(url);
                    ImageS3 imageS3 = ImageS3.builder()
                            .uploadUrl(url)
                            .fileName(filename)
                            .community(challengeVarifySaved.getCommunity())
                            .build();
                    imageS3Repository.save(imageS3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return challengeVarifySaved.getCommunity().getId();
    }


    @Override
    public CommunityResponseDto getChallengeVarify(Long communityId, CustomUserDetails customUserDetails) {

        ChallengeVarify challengeVarify = findChallengeVarifyById(communityId);
        Community community=challengeVarify.getCommunity();

        //deleteYn=true인지 확인하는 로직
        commonFuntion.deleteCommunity(community.getId());

        community.setViewCount(community.getViewCount()+1);
        communityRepository.save(community);

        boolean isAuthor;
        if(customUserDetails!=null){
            isAuthor= customUserDetails.getUser().getId().equals(community.getUser().getId());
        }
        else{
            isAuthor=false;
        }
        return new CommunityResponseDto(challengeVarify,isAuthor);
    }

    @Override
    public Page<CommunityListResponseDto> getChallengeVarifies(int page, int size, String sortBy, boolean isAsc) {
        // 정렬 방향 설정
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<ChallengeVarify> challengeVarifyPage = challengeVarifyRepository.findAllByCommunityDeleteYnFalseOrderByCreatedDateDesc(pageable);
        return challengeVarifyPage.map(CommunityListResponseDto::new);
    }

    @Override
    public Long updateChallengeVarify(Long communityId, CommunityRequestDto requestDto, User user) {

        ChallengeVarify challengeVarify = findChallengeVarifyById(communityId);

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

                    ImageS3 imageS3 = ImageS3.builder()
                            .uploadUrl(url)
                            .fileName(filename)
                            .community(community)
                            .build();

                    imageS3Repository.save(imageS3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        community.update(requestDto);
        ChallengeVarify saveChallengeVarify=challengeVarifyRepository.save(challengeVarify);
        return saveChallengeVarify.getCommunity().getId();
    }

    @Override
    public void deleteChallengeVarify(Long communityId, User user) {

        ChallengeVarify challengeVarify = findChallengeVarifyById(communityId);

        //deleteYn=true인지 확인하는 로직
        commonFuntion.deleteCommunity(challengeVarify.getCommunity().getId());


        if(!challengeVarify.getCommunity().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
        }
        challengeVarify.getCommunity().setDeleteYn(true);
        challengeVarifyRepository.save(challengeVarify);
    }

    public ChallengeVarify findChallengeVarifyById(Long communityId) {
        return challengeVarifyRepository.findByCommunityId(communityId).orElseThrow(
                ()-> new NotFoundException(ErrorCode.CHALLENGE_NOT_FOUND));
    }
}

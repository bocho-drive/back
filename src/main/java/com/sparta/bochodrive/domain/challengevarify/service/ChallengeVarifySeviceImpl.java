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
    public Long addChallengeVarify(ChallengeVarifyRequestDto requestDto, User user) throws IOException {

        commonFuntion.existsById(user.getId());

        //게시글 객체 생성 후 저장
        Community community=new Community(requestDto,user);
        Community savedCommunity=communityRepository.save(community);

        Challenge challenge=challengeRepository.findById(requestDto.getChallengeId()).get();


        ChallengeVarify challengeVarify=new ChallengeVarify(user,savedCommunity,challenge);
        //이미지 파일 리스트
        List<MultipartFile> requestImages=requestDto.getImages();
        ChallengeVarify challengeVarifySaved = challengeVarifyRepository.save(challengeVarify);

        if(requestImages!=null ||!requestImages.isEmpty()){
            for (MultipartFile file : requestImages) {
                String url=imageS3Service.upload(file);
                String filename=imageS3Service.getFileName(url);
                ImageS3 imageS3=new ImageS3(url,filename, challengeVarifySaved.getCommunity());
                imageS3Repository.save(imageS3);
            }

        }

        return challengeVarifySaved.getId();
    }


    @Override
    public ChallengeVarifyResponseDto getChallengeVarify(Long id) {

        ChallengeVarify challengeVarify = findChallengeVarifyById(id);
        Community community=challengeVarify.getCommunity();
        community.setViewCount(community.getViewCount()+1);
        communityRepository.save(community);
        return new ChallengeVarifyResponseDto(challengeVarify);
    }

    @Override
    public Page<ChallengeVarifyResponseDto> getChallengeVarifies(int page, int size, String sortBy, boolean isAsc) {
        // 정렬 방향 설정
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<ChallengeVarify> challengeVarifyPage = challengeVarifyRepository.findAllByOrderByCreatedAtDesc(pageable);
        return challengeVarifyPage.map(ChallengeVarifyResponseDto::new);

    }

    @Override
    public Long updateChallengeVarify(Long id, ChallengeVarifyRequestDto requestDto, User user) throws IOException {
        commonFuntion.existsById(user.getId());
        ChallengeVarify challengeVarify = findChallengeVarifyById(id);
        Community community=challengeVarify.getCommunity();

        if(!challengeVarify.getCommunity().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.UPDATE_FAILED);
        }

        List<ImageS3> originImages=imageS3Repository.findAllByCommunityId(challengeVarify.getCommunity().getId());
        if(!originImages.isEmpty() && originImages!=null) {
            for(ImageS3 originImage:originImages){
                imageS3Service.deleteFile(originImage.getFileName());
                imageS3Repository.delete(originImage);
            }
            for(MultipartFile file : requestDto.getImages()) {
                String url=imageS3Service.upload(file);
                String filename=imageS3Service.getFileName(url);
                ImageS3 imageS3=new ImageS3(url,filename, challengeVarify.getCommunity());
                imageS3Repository.save(imageS3);
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

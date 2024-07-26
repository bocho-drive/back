package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;

import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {


    private final CommunityRepository communityRepository;
    private final CommonFuntion commonFuntion;


    // 게시글 작성
    @Override
    @Transactional
    public CommunityResponseDto addPost(CommunityRequestDto communityRequestDto, User user)  {

        commonFuntion.existsById(user.getId());
        Community community = new Community(communityRequestDto,user);
        Community savedCommunity = communityRepository.save(community);
        return new CommunityResponseDto(savedCommunity);
    }


    //게시글 목록 조회
    @Override
    public List<CommunityListResponseDto> getAllPosts(CategoryEnum category)  {
        //카테고리 별로 list찾기
        List<Community> communities;
        //카테고리별 목록 조회
        if(category == null) {
            communities = communityRepository.findAllByOrderByCreatedAtDesc(); //카테고리 설정 안했을 때 전체글 목록
        }
        else {
            communities=communityRepository.findAllByCategory(category); //카테고리별 글목록
        }

        return communities.stream().map(communityListResponseDto ->
                new CommunityListResponseDto(communityListResponseDto)).collect(Collectors.toList());
    }

    //게시글 상세 조회
    @Override
    public CommunityResponseDto getPost(Long id) {

        Community community=findCommunityById(id);
        CommunityResponseDto communityResponseDto = new CommunityResponseDto(community);
        return communityResponseDto;
    }
    //게시글 수정
    @Override
    @Transactional
    public void updatePost(Long id, CommunityRequestDto communityRequestDto,User user) {

        commonFuntion.existsById(user.getId()); //userId가 userRepository에 존재하는지에 관한 예외처리
        Community community=findCommunityById(id);

        //게시글 community의 userId와 수정하려는 사람의 userId가 같은지에 관한 에외처리

        if(!community.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
        }
        community.update(communityRequestDto);//update
        communityRepository.save(community);

    }

    //게시글 삭제
    @Override
    public void deletePost(Long id, User user)  {

        commonFuntion.existsById(user.getId());
        Community community=findCommunityById(id);
        if(!community.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
        }
        community.setDeleteYn(true);
        communityRepository.save(community);

    }


    //게시글 id 찾는 메소드
    public Community findCommunityById(Long id) {
        Community community=communityRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return community;
    }


}



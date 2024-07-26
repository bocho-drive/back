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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {


    private final CommunityRepository communityRepository;



    // 게시글 작성
    @Override
    @Transactional
    public CommunityResponseDto addPost(CommunityRequestDto communityRequestDto, User user)  {
        Community community = new Community(communityRequestDto, user);
//        //오늘의 질문!!! ->equals인지 signup뭐시기 인지
//        if(!community.getUser().getId().equals(user.getId())) {
//            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
//        }
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
            communities = communityRepository.findAll(); //카테고리 설정 안했을 때 전체글 목록
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

        Community community=findCommunityById(id);
//        //오늘의 질문!!! ->equals인지 signup뭐시기 인지
//        if(!community.getUser().getId().equals(user.getId())) {
//            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
//        }
        community.update(communityRequestDto);//update
        communityRepository.save(community);

    }

    //게시글 삭제
    @Override
    @Transactional
    public void deletePost(Long id, User user)  {

        Community community=findCommunityById(id);
        //
//        if(!community.getUser().getId().equals(user.getId())) {
//            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
//        }
        community.setDeleteYn(true);
        communityRepository.save(community);

    }


    //게시글 id 찾는 메소드
    public Community findCommunityById(Long id) {
        Community community=communityRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return community;
    }


}



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
    public CommunityResponseDto addPost(CommunityRequestDto communityRequestDto, User user) throws Exception {
        try {

            Community community = new Community(communityRequestDto, user);
            Community savedCommunity = communityRepository.save(community);
            return new CommunityResponseDto(savedCommunity);
        } catch (Exception e) {
            throw new Exception(ErrorCode.ADD_FAILED.getMessage(), e);
        }
    }


    //게시글 목록 조회
    @Override
    public List<CommunityListResponseDto> getAllPosts(CategoryEnum category) throws Exception {
        try{
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
        }catch (Exception e){
            throw new Exception(ErrorCode.ADD_FAILED.getMessage());
        }
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
    public void updatePost(Long id, CommunityRequestDto communityRequestDto) throws Exception{

        try{
            Community community=findCommunityById(id);
            community.update(communityRequestDto);//update
            communityRepository.save(community);
        }catch (Exception e){
            throw new Exception(ErrorCode.UPDATE_FAILED.getMessage());
        }

    }

    //게시글 삭제
    @Override
    public void deletePost(Long id)  throws Exception {

        //궁금한 것 데이터베이스에서 deleteYn이 있는데
        //이걸 한 이유가 사용자가 글을 삭제한다고 해서 진짜 삭제하는게 아니라 정보는 가지고 있는 걸 구분하기 위한 컬럼인데
        //이걸 어떻게 처리할지 모르겠습니다.
        try{
            Community community=findCommunityById(id);
            community.setDeleteYn(true);
            communityRepository.save(community);
        }catch (Exception e){
            throw new Exception(ErrorCode.DELETE_FAILED.getMessage());
        }
    }


    //게시글 id 찾는 메소드
    public Community findCommunityById(Long id) {
        Community community=communityRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return community;
    }


}



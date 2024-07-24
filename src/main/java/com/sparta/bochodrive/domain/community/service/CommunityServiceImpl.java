package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;



    //게시글 작성
    @Override
    public CommunityResponseDto addPost(CommunityRequestDto communityRequestDto, User user) {
        Community community = new Community(communityRequestDto, user);
        Community savedCommunity = communityRepository.save(community);
        return new CommunityResponseDto(savedCommunity);

    }

    //게시글 목록 조회
    public List<CommunityListResponseDto> getAllPosts(CategoryEnum category) {
        //카테고리 별로 list찾기
        List<Community> communities;
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
    public CommunityResponseDto getPost(Long id) {
        Community community=findCommunityById(id);
        CommunityResponseDto communityResponseDto = new CommunityResponseDto(community);
        communityResponseDto.addViewCount(); //조회수++
        return communityResponseDto;
    }
    //게시글 수정
    @Transactional
    public void updatePost(Long id, CommunityRequestDto communityRequestDto) {

        try{
            Community community=findCommunityById(id);
            community.update(communityRequestDto);//update

        }catch (IllegalArgumentException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }

    //게시글 삭제
    public void deletePost(Long id, UserDetailsImpl userDetails) throws AccessDeniedException {

        Community community=findCommunityById(id);
        // 현재 사용자가 게시글 작성자인지 확인
        if (!community.getUser().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("게시글 작성자가 아닙니다.");
        }
        communityRepository.delete(community); //삭제
    }


    //게시글 id 찾는 메소드
    public Community findCommunityById(Long id) {
        Optional<Community> community=communityRepository.findById(id);
        if(community.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }
        return community.get();
    }


}



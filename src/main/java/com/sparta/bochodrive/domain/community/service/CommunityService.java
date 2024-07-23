package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;



    //게시글 작성
    public CommunityResponseDto addPost(CommunityRequestDto communityRequestDto, User user) {
        Community community = new Community(communityRequestDto, user);
        Community savedCommunity = communityRepository.save(community);
        return new CommunityResponseDto(savedCommunity);

    }

    //게시글 목록 조회
    public List<CommunityListResponseDto> getAllPosts(String category) {
        //카테고리 별로 list찾기
        List<Community> communities;
        if(category == null || category.isEmpty()) {
            communities = communityRepository.findAll();
        }
        else {
            communities=communityRepository.findAllByCategory(category);

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
    public CommunityResponseDto updatePost(Long id,
                                           CommunityRequestDto communityRequestDto,
                                           UserDetailsImpl userDetails) throws AccessDeniedException {

        Community community=findCommunityById(id);
        // 현재 사용자가 게시글 작성자인지 확인
        if (!community.getUser().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("게시글 작성자가 아닙니다.");
        }
        community.update(communityRequestDto);
        communityRepository.save(community);

        return new CommunityResponseDto(community);
    }

    //게시글 삭제
    public void deletePost(Long id, UserDetailsImpl userDetails) throws AccessDeniedException {
        Community community=findCommunityById(id);
        // 현재 사용자가 게시글 작성자인지 확인
        if (!community.getUser().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("게시글 작성자가 아닙니다.");
        }
        communityRepository.delete(community);


    }


    //게시글 id 찾는 메소드
    public Community findCommunityById(Long id) {
        return communityRepository.findById(id).orElse(null);
    }


}



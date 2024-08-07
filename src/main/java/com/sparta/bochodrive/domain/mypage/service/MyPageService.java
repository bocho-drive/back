package com.sparta.bochodrive.domain.mypage.service;

import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.mypage.dto.MyPageChallengeVarifyListResponseDto;
import com.sparta.bochodrive.domain.mypage.dto.MyProfileResponseDto;
import com.sparta.bochodrive.domain.mypage.dto.MypageCommunityListResponseDto;
import org.springframework.data.domain.Page;

public interface MyPageService {
    MyProfileResponseDto getMyProfile(Long id);

    // 게시글 목록 불러오기
    MypageCommunityListResponseDto getMyPosts(Long userid, int page, int size, String sortBy, boolean isAsc, CategoryEnum category);

    // 댓글 목록 불러오기
    Page<CommentResponseDto> getMyComments(Long userId, int page, int size, String sortBy, boolean isAsc);

    // 챌린지 인증 목록 불러오기
    Page<MyPageChallengeVarifyListResponseDto> getMyChallenges(Long userId, int page, int size, String sortBy, boolean isAsc);
}

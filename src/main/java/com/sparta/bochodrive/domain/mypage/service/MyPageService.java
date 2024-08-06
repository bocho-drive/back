package com.sparta.bochodrive.domain.mypage.service;

import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingListDto;
import org.springframework.data.domain.Page;

public interface MyPageService {
    // 게시글 목록 불러오기
    Page<CommunityListResponseDto> getMyPosts(Long userid, int page, int size, String sortBy, boolean isAsc);

    // 댓글 목록 불러오기
    Page<CommentResponseDto> getMyComments(Long userId, int page, int size, String sortBy, boolean isAsc);

    // 챌린지 인증 목록 불러오기
    Page<CommunityListResponseDto> getMyChallenges(Long userId, int page, int size, String sortBy, boolean isAsc);

    // 드라이브매칭 목록 불러오기
    Page<DriveMatchingListDto> getMyMatchingAppies(Long userId, int page, int size, String sortBy, boolean isAsc);

}

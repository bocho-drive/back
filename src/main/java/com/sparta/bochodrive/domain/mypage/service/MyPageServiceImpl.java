package com.sparta.bochodrive.domain.mypage.service;

import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.challengevarify.repository.ChallengeVarifyRepository;
import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.comment.repository.CommentRepository;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.drivematching.entity.DriveMatching;
import com.sparta.bochodrive.domain.drivematching.repository.DriveMatchingRepository;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingListDto;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.repository.DriveMatchingApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final CommentRepository commentRepository;
    private final ChallengeVarifyRepository challengeVarifyRepository;
    private final CommunityRepository communityRepository;
    private final DriveMatchingRepository driveMatchingRepository;
    private final DriveMatchingApplyRepository driveMatchingApplyRepository;

    // 게시글 목록 불러오기
    @Override
    public Page<CommunityListResponseDto> getMyPosts(Long userid, int page, int size, String sortBy, boolean isAsc) {

        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        Page<Community> communities = communityRepository.findByUserIdAndDeleteYNFalse(userid, pageable);
        return communities.map(this::convertCommunityToDto);
    }

    private CommunityListResponseDto convertCommunityToDto(Community community) {
        return CommunityListResponseDto.builder()
                .id(community.getId())
                .title(community.getTitle())
                .author(community.getUser().getNickname())
                .createdAt(community.getCreatedAt())
                .verifiedYN(community.isVerifiedYN())
                .viewCount(community.getViewCount())
                .likeCount(community.getLikeCount())
                .build();
    }

    // 댓글 목록 불러오기
    @Override
    public Page<CommentResponseDto> getMyComments(Long userId, int page, int size, String sortBy, boolean isAsc) {

        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        Page<Comment> comments = commentRepository.findByUserIdAndDeleteYNFalse(userId, pageable);
        return comments.map(this::convertCommentToDto);
    }


    private CommentResponseDto convertCommentToDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }


    // 챌린지 인증 목록 불러오기
    @Override
    public Page<CommunityListResponseDto> getMyChallenges(Long userId, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        Page<ChallengeVarify> challengeVarifies = challengeVarifyRepository.findByUserId(userId, pageable);
        return challengeVarifies.map(this::convertChallengeVerifyToDto);
    }



    private CommunityListResponseDto convertChallengeVerifyToDto(ChallengeVarify challengeVarify) {
        return CommunityListResponseDto.builder()
                .id(challengeVarify.getId())
                .title(challengeVarify.getCommunity().getTitle())
                .author(challengeVarify.getCommunity().getUser().getNickname())
                .createdAt(challengeVarify.getCreatedAt())
                .verifiedYN(challengeVarify.getCommunity().isVerifiedYN())
                .viewCount(challengeVarify.getCommunity().getViewCount())
                .likeCount(challengeVarify.getCommunity().getLikeCount())
                .build();

    }

    @Override
    public Page<DriveMatchingListDto> getMyMatchingAppies(Long userId, int page, int size, String sortBy, boolean isAsc) {
        List<DriveMatchingListDto> result = new ArrayList<>();

        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        Page<DriveMatching> matchingList = driveMatchingRepository.findAllByUserId(userId, pageable);
        for (DriveMatching driveMatching : matchingList) {
            List<DriveMatchingApply> matchingApplyList = driveMatchingApplyRepository.findDriveMatchingApplyByDriveMatchingId(driveMatching.getId());
            for (DriveMatchingApply matchingApply : matchingApplyList) {
                result.add(convertDriveMatchingToDto(driveMatching, matchingApply));
            }
        }
        return new PageImpl<>(result, pageable, result.size());
    }
    private DriveMatchingListDto convertDriveMatchingToDto(DriveMatching driveMatching, DriveMatchingApply driveMatchingApply) {
        return DriveMatchingListDto.builder()
                .matchingId(driveMatching.getId())
                .title(driveMatching.getTitle())
                .content(driveMatching.getContent())
                .status(driveMatching.getStatus())
                .deleteYn(driveMatching.getDeleteYN())
                .applyId(driveMatchingApply.getId())
                .teacherId(driveMatchingApply.getUser().getId())
                .teacherNickname(driveMatchingApply.getUser().getNickname())
                .build();

    }

    private Pageable createPageRequest(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction;
        if (isAsc) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        return PageRequest.of(page, size, direction, sortBy);
    }


}

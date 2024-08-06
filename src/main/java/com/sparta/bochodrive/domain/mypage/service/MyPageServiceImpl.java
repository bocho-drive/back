package com.sparta.bochodrive.domain.mypage.service;

import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.challengevarify.repository.ChallengeVarifyRepository;
import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.comment.repository.CommentRepository;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.mypage.dto.MypageCommunityListResponseDto;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final CommentRepository commentRepository;
    private final ChallengeVarifyRepository challengeVarifyRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    // 게시글 목록 불러오기
    @Override
    public MypageCommunityListResponseDto getMyPosts(Long userid, int page, int size, String sortBy, boolean isAsc) {

        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        Page<Community> communities = communityRepository.findByUserIdAndDeleteYNFalse(userid, pageable);

        Optional<User> user = userRepository.findById(userid);
        return new MypageCommunityListResponseDto(user.get(), communities);
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
                .userId(comment.getUser().getId())
                .author(comment.getUser().getNickname())
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

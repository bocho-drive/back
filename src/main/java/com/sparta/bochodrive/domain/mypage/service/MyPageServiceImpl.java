package com.sparta.bochodrive.domain.mypage.service;

import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.challengevarify.repository.ChallengeVarifyRepository;
import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.comment.repository.CommentRepository;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.mypage.dto.MyPageChallengeVarifyListResponseDto;
import com.sparta.bochodrive.domain.mypage.dto.MyProfileResponseDto;
import com.sparta.bochodrive.domain.mypage.dto.MypageCommunityListResponseDto;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
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


    @Override
    public MyProfileResponseDto getMyProfile(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return new MyProfileResponseDto(user.get());
    }

    // 게시글 목록 불러오기
    @Override
    public MypageCommunityListResponseDto getMyPosts(Long userid, int page, int size, String sortBy, boolean isAsc, CategoryEnum category) {

        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        Page<Community> communities = communityRepository.findByUserIdAndCategoryAndDeleteYNFalse(userid, category, pageable);

        return new MypageCommunityListResponseDto(category, communities);
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
    public Page<MyPageChallengeVarifyListResponseDto> getMyChallenges(Long userId, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = createPageRequest(page, size, sortBy, isAsc);
        Page<ChallengeVarify> challengeVarifies = challengeVarifyRepository.findByUserId(userId, pageable);
        return challengeVarifies.map(this::convertChallengeVerifyToDto);
    }



    private MyPageChallengeVarifyListResponseDto convertChallengeVerifyToDto(ChallengeVarify challengeVarify) {

        return new MyPageChallengeVarifyListResponseDto(challengeVarify);

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

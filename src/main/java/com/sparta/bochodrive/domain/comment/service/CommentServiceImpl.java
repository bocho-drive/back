package com.sparta.bochodrive.domain.comment.service;

import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.comment.repository.CommentRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final CommonFuntion commonFunction;

    @Override
    public CommentResponseDto addComments(CommentRequestDto commentRequestDto, User user) {

        commonFunction.existsById(user.getId());
        log.info("사용자 ID 검증 완료: {}", user.getId());

        Community community = findCommunityById(commentRequestDto.getCommunityId());
        log.info("커뮤니티 검증 완료: {}", community.getId());

        Comment comment = Comment.builder()
                .community(community)
                .user(user)
                .content(commentRequestDto.getContent())
                .deleteYN(false)
                .build();
        log.info("생성된 댓글 엔티티: {}", comment);

        Comment savedComment = commentRepository.save(comment);
        log.info("저장된 댓글 엔티티: {}", savedComment);

        return new CommentResponseDto(savedComment);
    }

    @Override
    public List<CommentResponseDto> getComments(Long communitiesId) {

        List<Comment> commentList=commentRepository.findByCommunityIdAndDeleteYNFalse(communitiesId);
        return commentList.stream().map(commentResponseDto ->
                new CommentResponseDto(commentResponseDto)).collect(Collectors.toList());
    }

    @Override
    public void updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {

        commonFunction.existsById(user.getId());
        Comment comment = findCommentById(commentId);
        log.info("comment : {}",comment.getId());

        //댓글 작성자가 맞는지 확인
        if(!comment.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException(ErrorCode.UPDATE_FAILED);
        }
        comment.update(commentRequestDto);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId,User user)  {

        commonFunction.existsById(user.getId());
        Comment comment = findCommentById(commentId);

        if(!comment.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
        }
        comment.updateDeleteYN(true);
        commentRepository.save(comment);
    }

    private Community findCommunityById(Long id) {
        log.info("커뮤니티 조회 요청 ID: {}", id);
        return communityRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("커뮤니티 조회 실패: ID = {}", id);
                    return new IllegalArgumentException("Invalid community ID");
                });
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }
}

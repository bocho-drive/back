package com.sparta.bochodrive.domain.comment.service;

import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.comment.repository.CommentRepository;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;


    @Override
    public CommentReponseDto addComments(CommentRequestDto commentRequestDto, User user) {

        Community community=findCommunityById(commentRequestDto.getCommunitiesId());
        if(community.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException(ErrorCode.ADD_FAILED);
        }
        Comment comment = new Comment(commentRequestDto, user, community);
        Comment savedComment = commentRepository.save(comment);
        return new CommentReponseDto(savedComment);

    }


    @Override
    public List<CommentReponseDto> getComments(Long communitiesId) {

        List<Comment> commentList=commentRepository.findByCommunityId(communitiesId);
        return commentList.stream().map(commentResponseDto ->
                new CommentReponseDto(commentResponseDto)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ErrorCode updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = findCommentById(commentId);
        if(comment.getUser().getId().equals(commentRequestDto.getCommunitiesId())){
            return ErrorCode.UPDATE_FAILED;
        }
        comment.update(commentRequestDto);
        commentRepository.save(comment);
        return ErrorCode.OK;

    }

    @Override
    public ErrorCode deleteComment(Long commentId,User user)  {
        Comment comment = findCommentById(commentId);
        if(comment.getUser().getId().equals(user.getId())){
            return ErrorCode.DELETE_FAILED;
        }
        comment.setDeleteYN(true);
        commentRepository.save(comment);
        return ErrorCode.OK;

    }

    public Community findCommunityById(Long communityId)  {
        Community community = communityRepository.findById(communityId).orElseThrow(()->new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return community;
    }
    public Comment findCommentById(Long commentId) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        return comment;
    }
}

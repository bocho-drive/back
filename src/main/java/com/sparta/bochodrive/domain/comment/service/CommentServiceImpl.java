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
    public CommentReponseDto addComments(CommentRequestDto commentRequestDto, User user) throws Exception {

        try{
            Community community=findCommunityById(commentRequestDto.getCommunitiesId());
            Comment comment = new Comment(commentRequestDto, user, community);
            Comment savedComment = commentRepository.save(comment);
            return new CommentReponseDto(savedComment);
        }catch (Exception e){
            throw new Exception(ErrorCode.ADD_FAILED.getMessage());
        }

    }


    @Override
    public List<CommentReponseDto> getComments(Long communitiesId) throws Exception {


        try{
            List<Comment> commentList=commentRepository.findByCommunityId(communitiesId);
            return commentList.stream().map(commentResponseDto ->
                    new CommentReponseDto(commentResponseDto)).collect(Collectors.toList());
        }catch (Exception e){
            throw new Exception(ErrorCode.GETLIST_FAILED.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateComment(Long commentId, CommentRequestDto commentRequestDto) throws Exception {
       try{
           Comment comment = findCommentById(commentId);
           comment.update(commentRequestDto);
           commentRepository.save(comment);
       }catch (Exception e){
           throw new Exception(ErrorCode.UPDATE_FAILED.getMessage());
       }

    }

    @Override
    public void deleteComment(Long commentId) throws Exception {
        try{
            Comment comment = findCommentById(commentId);
            comment.setDeleteYN(true);
            commentRepository.save(comment);
        }catch (Exception e){
            throw new Exception(ErrorCode.DELETE_FAILED.getMessage());
        }

    }

    public Community findCommunityById(Long communityId) throws Exception {
        Community community = communityRepository.findById(communityId).orElseThrow(()->new Exception(ErrorCode.POST_NOT_FOUND.getMessage()));
        return community;
    }
    public Comment findCommentById(Long commentId) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        return comment;
    }
}

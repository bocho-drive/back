package com.sparta.bochodrive.domain.comment.service;

import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.comment.repository.CommentRepository;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;

    public CommentReponseDto addComments(CommentRequestDto commentRequestDto, User user) {
        Community community=findCommunityById(commentRequestDto.getCommunitiesId());
        Comment comment = new Comment(commentRequestDto, user, community);
        Comment savedComment = commentRepository.save(comment);
        return new CommentReponseDto(savedComment);

    }

    //다시 쓰기
    public List<CommentReponseDto> getComments(Long communitiesId) {

        List<Comment> commentList=commentRepository.findByCommunitiesId(communitiesId);
        List<CommentReponseDto> commentReponseDtoList=new ArrayList<>();
        for(Comment comment:commentList) {
            commentReponseDtoList.add(new CommentReponseDto(comment));

        }
        return commentReponseDtoList;
    }

    @Transactional
    public CommentReponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto,UserDetailsImpl userDetails) {
        Comment comment = findCommentById(commentId);
        if(!comment.getUser().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("작성자가 아닙니다.");
        }
        comment.update(commentRequestDto);
        commentRepository.save(comment);
        return new CommentReponseDto(comment);
    }

    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        //이것도 작성한 user맞는지 확인
        Comment comment = findCommentById(commentId);
        if(!comment.getUser().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("작성자가 아닙니다.");
        }
        commentRepository.delete(comment);

    }

    public Community findCommunityById(Long communityId) {
        Community community = communityRepository.findById(communityId).orElse(null);
        return community;
    }
    public Comment findCommentById(Long commentId) {
        Comment comment=commentRepository.findById(commentId).orElse(null);
        return comment;
    }
}

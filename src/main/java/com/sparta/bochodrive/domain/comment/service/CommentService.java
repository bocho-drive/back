package com.sparta.bochodrive.domain.comment.service;

import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.UserDetailsImpl;

import java.util.List;

public interface CommentService {
    CommentReponseDto addComments(CommentRequestDto commentRequestDto, User user);
    List<CommentReponseDto> getComments(Long communitiesId);
    CommentReponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails);
    void deleteComment(Long commentId, UserDetailsImpl userDetails);
}

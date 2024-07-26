package com.sparta.bochodrive.domain.comment.service;

import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;
//import com.sparta.bochodrive.global.UserDetailsImpl;

import java.util.List;

public interface CommentService {
    CommentReponseDto addComments(CommentRequestDto commentRequestDto, User user);
    List<CommentReponseDto> getComments(Long communitiesId);
    void updateComment(Long commentId, CommentRequestDto commentRequestDto, User user); ;
    void deleteComment(Long commentId,User user);
}

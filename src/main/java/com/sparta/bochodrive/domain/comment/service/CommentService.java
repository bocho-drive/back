package com.sparta.bochodrive.domain.comment.service;

import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.user.entity.User;
//import com.sparta.bochodrive.global.UserDetailsImpl;

import java.util.List;

public interface CommentService {
    CommentReponseDto addComments(CommentRequestDto commentRequestDto, User user) throws Exception;
    List<CommentReponseDto> getComments(Long communitiesId) throws Exception;
    void updateComment(Long commentId, CommentRequestDto commentRequestDto) throws Exception;
    void deleteComment(Long commentId) throws Exception;
}

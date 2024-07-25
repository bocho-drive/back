package com.sparta.bochodrive.domain.comment.controller;


import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.comment.service.CommentServiceImpl;
import com.sparta.bochodrive.domain.user.entity.User;
//import com.sparta.bochodrive.global.UserDetailsImpl;
import com.sparta.bochodrive.global.UserDetailsImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;

    //댓글 작성
    @PostMapping
    public ApiResponse addComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                          /*@AuthenticationPrincipal*/ User user) {

        commentService.addComments(commentRequestDto,user);
        return ApiResponse.ok(HttpStatus.OK.value(), "댓글 작성에 성공하였습니다.");


    }

    //댓글 전체 조회
    @GetMapping
    public ResponseEntity<?> getComments(@RequestParam(required = false) Long communitiesId){

        List<CommentReponseDto> comment = commentService.getComments(communitiesId);
        return ResponseEntity.ok().body(comment);

    }

    //댓글 수정
    @PutMapping("/{id}")
    public ApiResponse updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.updateComment(commentId,commentRequestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "수정에 성공하였습니다.");

    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    public ApiResponse deleteComment(@PathVariable Long commentId
            , @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.deleteComment(commentId,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "삭제에 성공하였습니다.");

    }








}

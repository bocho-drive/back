package com.sparta.bochodrive.domain.comment.controller;


import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.comment.service.CommentService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
//import com.sparta.bochodrive.global.UserDetailsImpl;
import com.sparta.bochodrive.global.UserDetailsImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ApiResponse<CommentResponseDto> addComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {


        commentService.addComments(commentRequestDto, customUserDetails.getUser());


        return ApiResponse.ok(HttpStatus.OK.value(), "댓글 작성에 성공하였습니다.");
    }

    // 댓글 전체 조회
    @GetMapping
    public ApiResponse<List<CommentResponseDto>> getComments(@RequestParam(value = "communityId", required = false) Long communitiesId) {
        log.info("댓글 전체 조회 요청: communitiesId = {}", communitiesId);

        List<CommentResponseDto> comments = commentService.getComments(communitiesId);
        log.info("댓글 전체 조회 성공: {}개의 댓글 조회됨", comments.size());

        return ApiResponse.ok(HttpStatus.OK.value(), "댓글 조회에 성공하였습니다.", comments);
    }

    //댓글 수정
    @PutMapping("/{id}")
    public ApiResponse updateComment(@PathVariable("id") Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentService.updateComment(commentId,commentRequestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "수정에 성공하였습니다.");

    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    public ApiResponse deleteComment(@PathVariable("id") Long commentId
            , @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentService.deleteComment(commentId,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "삭제에 성공하였습니다.");

    }








}

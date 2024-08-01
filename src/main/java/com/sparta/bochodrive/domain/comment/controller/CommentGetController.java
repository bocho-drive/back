package com.sparta.bochodrive.domain.comment.controller;

import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.comment.service.CommentService;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentGetController {

    private final CommentService commentService;

    // 댓글 전체 조회
    @GetMapping
    public ApiResponse<List<CommentResponseDto>> getComments(@RequestParam(value = "communityId", required = false) Long communitiesId) {
        log.info("댓글 전체 조회 요청: communitiesId = {}", communitiesId);

        List<CommentResponseDto> comments = commentService.getComments(communitiesId);
        log.info("댓글 전체 조회 성공: {}개의 댓글 조회됨", comments.size());

        return ApiResponse.ok(HttpStatus.OK.value(), "댓글 조회에 성공하였습니다.", comments);
    }
}

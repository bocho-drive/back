package com.sparta.bochodrive.domain.comment.controller;


import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.comment.service.CommentServiceImpl;
import com.sparta.bochodrive.global.UserDetailsImpl;
import com.sparta.bochodrive.global.entity.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{

        commentService.addComments(commentRequestDto,userDetails.getUser());
        Message message = new Message(HttpStatus.CREATED.value(), "댓글 작성에 성공하였습니다.");
        return new ResponseEntity<>(message, HttpStatus.CREATED);


    }

    //댓글 전체 조회
    @GetMapping
    public ResponseEntity<?> getComments(@RequestParam(required = false) Long communitiesId) throws Exception{

        List<CommentReponseDto> comment = commentService.getComments(communitiesId);
        return ResponseEntity.ok().body(comment);

    }

    //댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto) throws Exception{

        commentService.updateComment(commentId,commentRequestDto);
        Message message=new Message(HttpStatus.OK.value(),"수정에 성공하였습니다");
        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) throws Exception {

        commentService.deleteComment(commentId);
        Message message=new Message(HttpStatus.OK.value(),"수정에 성공하였습니다");
        return new ResponseEntity<>(message,HttpStatus.OK);

    }








}

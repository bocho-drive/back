package com.sparta.bochodrive.domain.comment.controller;


import com.sparta.bochodrive.domain.comment.dto.CommentReponseDto;
import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.comment.service.CommentService;
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
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            commentService.addComments(commentRequestDto,userDetails.getUser());
            Message message = new Message(HttpStatus.CREATED, "댓글 작성에 성공하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }catch (Exception e) {
            Message message = new Message(HttpStatus.UNAUTHORIZED, "작성에 실패하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

    }

    //댓글 전체 조회
    @GetMapping
    public ResponseEntity<?> getComments(@RequestParam(required = false) Long communitiesId) {
        try {
            List<CommentReponseDto> comment = commentService.getComments(communitiesId);
            return ResponseEntity.ok().body(comment);
        }catch (Exception e) {
            Message message = new Message(HttpStatus.UNAUTHORIZED, "조회에 실패하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

    //댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            commentService.updateComment(commentId,commentRequestDto,userDetails);
            Message message=new Message(HttpStatus.OK,"수정에 성공하였습니다",null);
            return new ResponseEntity<>(message,HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            Message message=new Message(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다.",null);
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            Message message=new Message(HttpStatus.UNAUTHORIZED,"수정에 실패하였습니다.",null);
            return new ResponseEntity<>(message,HttpStatus.UNAUTHORIZED);
        }
    }

    //댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            commentService.deleteComment(commentId,userDetails);
            Message message=new Message(HttpStatus.OK,"수정에 성공하였습니다",null);
            return new ResponseEntity<>(message,HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            Message message=new Message(HttpStatus.NOT_FOUND,"존재하지 않는 댓글입니다.",null);
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            Message message=new Message(HttpStatus.UNAUTHORIZED,"수정에 실패하였습니다.",null);
            return new ResponseEntity<>(message,HttpStatus.UNAUTHORIZED);
        }
    }








}

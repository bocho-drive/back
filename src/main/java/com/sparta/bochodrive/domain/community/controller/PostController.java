package com.sparta.bochodrive.domain.community.controller;


import com.sparta.bochodrive.domain.community.dto.PostListResponseDto;
import com.sparta.bochodrive.domain.community.dto.PostRequestDto;
import com.sparta.bochodrive.domain.community.service.PostService;
import com.sparta.bochodrive.global.UserDetailsImpl;
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
public class PostController {

    PostService postService;

    //게시글 작성
    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody @Valid PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            postService.addPost(postRequestDto,userDetails.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body("작성에 성공하였습니다.");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("작성에 실패하였습니다.");
        }

    }

    //게시글 목록 조회
    @GetMapping
    public ResponseEntity<?> getAllPosts(@RequestParam(required = false) String category) {
        try{
            List<PostListResponseDto> post=postService.getAllPosts(category);
            return ResponseEntity.status(HttpStatus.OK).body(post);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("조회 실패하였습니다");
        }
    }

    //게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return null;
    }

    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid PostRequestDto postRequestDto) {
        return null;
    }
    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return null;
    }


}

package com.sparta.bochodrive.domain.community.controller;


import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.service.CommunityServiceImpl;
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
public class CommunityController {

    CommunityServiceImpl communityService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody @Valid CommunityRequestDto postRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            communityService.addPost(postRequestDto, userDetails.getUser());
            Message message = new Message(HttpStatus.CREATED, "작성에 성공하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            Message message = new Message(HttpStatus.UNAUTHORIZED, "작성에 실패하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<?> getAllPosts(@RequestParam(required = false) String category) {
        try {
            List<CommunityListResponseDto> posts = communityService.getAllPosts(category);
            Message message = new Message(HttpStatus.OK, "목록 조회 성공", posts);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            Message message = new Message(HttpStatus.UNAUTHORIZED, "조회 실패하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        try {
            CommunityResponseDto post = communityService.getPost(id);
            return ResponseEntity.ok().body(post);
        }catch (IllegalArgumentException e) {
            Message message=new Message(HttpStatus.NOT_FOUND,"존재하지 않는 게시글입니다.",null);
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Message message = new Message(HttpStatus.UNAUTHORIZED, "조회에 실패하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                              @RequestBody @Valid CommunityRequestDto postRequestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            communityService.updatePost(id, postRequestDto, userDetails);
            Message message = new Message(HttpStatus.OK, "수정에 성공하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            Message message=new Message(HttpStatus.NOT_FOUND,"존재하지 않는 게시글입니다.",null);
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            Message message = new Message(HttpStatus.UNAUTHORIZED, "수정에 실패하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            communityService.deletePost(id, userDetails);
            Message message = new Message(HttpStatus.OK, "삭제에 성공하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            Message message=new Message(HttpStatus.NOT_FOUND,"존재하지 않는 게시글입니다.",null);
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            Message message = new Message(HttpStatus.UNAUTHORIZED, "삭제에 실패하였습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

}

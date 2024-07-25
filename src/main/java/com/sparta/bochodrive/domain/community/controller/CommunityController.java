package com.sparta.bochodrive.domain.community.controller;


import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
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
    public ResponseEntity addPost(@RequestBody @Valid CommunityRequestDto postRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws Exception {

        communityService.addPost(postRequestDto,userDetails.getUser());
        Message message = new Message(HttpStatus.CREATED.value(), "작성에 성공하였습니다.");
        return new ResponseEntity<>(message,HttpStatus.CREATED);

    }

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<?> getAllPosts(@RequestParam(required = false) CategoryEnum category) throws Exception{

        List<CommunityListResponseDto> posts = communityService.getAllPosts(category);
        Message message = new Message(HttpStatus.OK.value(), "목록 조회 성공",posts);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {

        CommunityResponseDto post = communityService.getPost(id);
        return ResponseEntity.ok().body(post);

    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity updatePost(@PathVariable Long id,
                                     @RequestBody @Valid CommunityRequestDto postRequestDto) throws Exception {

        communityService.updatePost(id, postRequestDto);
        Message message = new Message(HttpStatus.OK.value(), "수정에 성공하였습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) throws Exception{

        communityService.deletePost(id);
        Message message = new Message(HttpStatus.OK.value(), "삭제에 성공하였습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

}

package com.sparta.bochodrive.community.controller;


import com.sparta.bochodrive.community.dto.PostRequestDto;
import com.sparta.bochodrive.community.dto.PostResponseDto;
import com.sparta.bochodrive.community.service.PostService;
import com.sparta.bochodrive.global.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class PostController {

    PostService postService;

    //게시글 작성
    @PostMapping("/")
    public ResponseEntity<PostRequestDto> addPost(@RequestBody @Valid PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto=postService.addPost(postRequestDto,userDetails.getUser());
        return null;
    }


}

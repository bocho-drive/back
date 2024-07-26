package com.sparta.bochodrive.domain.community.controller;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.service.CommunityServiceImpl;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.UserDetailsImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommunityController {

    CommunityServiceImpl communityService;

    // 게시글 작성
    @PostMapping
    public ApiResponse addPost(@RequestBody @Valid CommunityRequestDto postRequestDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails)
      {

        communityService.addPost(postRequestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "게시글 작성에 성공하였습니다.");

    }



    // 게시글 수정
    @PutMapping("/{id}")
    public ApiResponse updatePost(@PathVariable Long id,
                                          @RequestBody @Valid CommunityRequestDto postRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails)  {

        communityService.updatePost(id, postRequestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "수정에 성공하였습니다.");

    }


    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ApiResponse deletePost(@PathVariable Long id,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        communityService.deletePost(id,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "삭제에 성공하였습니다.");

    }

}

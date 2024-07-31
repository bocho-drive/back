package com.sparta.bochodrive.domain.community.controller;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.community.service.CommunityService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    // 게시글 작성
    @PostMapping
    public ApiResponse<Long> addPost(@RequestBody @Valid CommunityRequestDto postRequestDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long communityId=communityService.addPost(postRequestDto, userDetails.getUser());
        log.info("게시글 작성 성공");
        return ApiResponse.ok(HttpStatus.OK.value(), "게시글 작성에 성공하였습니다.",communityId);

    }



    // 게시글 수정
    @PutMapping("/{id}")
    public ApiResponse<Long> updatePost(@PathVariable("id") Long id,
                                          @RequestBody @Valid CommunityRequestDto postRequestDto,
                                          @AuthenticationPrincipal CustomUserDetails userDetails)  {

        Long communityId=communityService.updatePost(id, postRequestDto,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "수정에 성공하였습니다.",communityId);

    }


    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ApiResponse deletePost(@PathVariable("id") Long id,
                                  @AuthenticationPrincipal CustomUserDetails userDetails){
        communityService.deletePost(id,userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "삭제에 성공하였습니다.");

    }

}

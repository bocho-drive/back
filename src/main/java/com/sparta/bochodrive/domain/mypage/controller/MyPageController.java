package com.sparta.bochodrive.domain.mypage.controller;


import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.dto.CommunityResponseDto;
import com.sparta.bochodrive.domain.mypage.service.MyPageServiceImpl;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("my")
public class MyPageController {

    private final MyPageServiceImpl myPageService;

    @GetMapping("{id}/posts")
    public ApiResponse<Page<CommunityListResponseDto>> getMyPosts(@PathVariable("id") Long id,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                                              @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                              @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {

        Page<CommunityListResponseDto> myPosts = myPageService.getMyPosts(id, page, size, sortBy, isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "마이페이지 조회에 성공하였습니다.", myPosts);
    }


    @GetMapping("{id}/comments")
    public ApiResponse<Page<CommentResponseDto>> getMyComments(@PathVariable("id") Long id,
                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                               @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                               @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {

        Page<CommentResponseDto> myComments = myPageService.getMyComments(id, page, size, sortBy, isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "마이페이지 댓글 조회에 성공하였습니다.", myComments);
    }


    @GetMapping("{id}/challenges")
    public ApiResponse<Page<CommunityListResponseDto>> getMyChallenges(@PathVariable("id") Long id,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                                   @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                   @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        Page<CommunityListResponseDto> myChallenges = myPageService.getMyChallenges(id, page, size, sortBy, isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "마이페이지 챌린지 인증 조회에 성공하였습니다.", myChallenges);
    }

}

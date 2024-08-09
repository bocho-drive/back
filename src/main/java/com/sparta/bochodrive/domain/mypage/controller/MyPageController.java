package com.sparta.bochodrive.domain.mypage.controller;


import com.sparta.bochodrive.domain.comment.dto.CommentResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.mypage.dto.MyPageChallengeVarifyListResponseDto;
import com.sparta.bochodrive.domain.mypage.dto.MyProfileResponseDto;
import com.sparta.bochodrive.domain.mypage.dto.MypageCommunityListResponseDto;
import com.sparta.bochodrive.domain.mypage.service.MyPageServiceImpl;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my")
public class MyPageController {

    private final MyPageServiceImpl myPageService;

    @GetMapping("/profile")
    public ApiResponse<MyProfileResponseDto> getMyProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        MyProfileResponseDto myProfile = myPageService.getMyProfile(customUserDetails.getUserId());
        return ApiResponse.ok(HttpStatus.OK.value(), "마이페이지 프로필 조회 성공하였습니다", myProfile);
    }


    @GetMapping("/posts")
    public ApiResponse<Page<MypageCommunityListResponseDto>> getMyPosts(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                                  @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                  @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
                                                                  @RequestParam(value = "category", defaultValue = "GENERAL") CategoryEnum category) {

        Page<MypageCommunityListResponseDto> myPosts = myPageService.getMyPosts(customUserDetails.getUserId(), page, size, sortBy, isAsc, category);
        return ApiResponse.ok(HttpStatus.OK.value(), "내 게시글 조회에 성공하였습니다.", myPosts);
    }



    @GetMapping("/comments")
    public ApiResponse<Page<CommentResponseDto>> getMyComments(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                               @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                               @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {

        Page<CommentResponseDto> myComments = myPageService.getMyComments(customUserDetails.getUserId(), page, size, sortBy, isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "내 댓글 조회에 성공하였습니다.", myComments);
    }


    @GetMapping("/challenges")
    public ApiResponse<Page<MyPageChallengeVarifyListResponseDto>> getMyChallenges(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                   @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                                   @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        Page<MyPageChallengeVarifyListResponseDto> myChallenges = myPageService.getMyChallenges(customUserDetails.getUserId(), page, size, sortBy, isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "내 챌린지 인증 조회에 성공하였습니다.", myChallenges);
    }

}

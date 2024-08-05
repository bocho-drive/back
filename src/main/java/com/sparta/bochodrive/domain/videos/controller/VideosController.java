package com.sparta.bochodrive.domain.videos.controller;

import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.videos.dto.VideoResDto;
import com.sparta.bochodrive.domain.videos.dto.VideosRequestDto;
import com.sparta.bochodrive.domain.videos.dto.VideosResponseDto;
import com.sparta.bochodrive.domain.videos.service.VideosService;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@Slf4j
public class VideosController {
    private final VideosService videosService;

    @PostMapping
    public ApiResponse<VideosResponseDto> addVideos(@RequestBody VideosRequestDto videosRequestDto,
                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        VideosResponseDto videosResponseDto = videosService.addVideos(videosRequestDto, customUserDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "연수관련 영상 등록에 성공하였습니다.", videosResponseDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteVideos(@PathVariable("id") Long videosId,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        videosService.deleteVideos(videosId, userDetails.getUser());
        return ApiResponse.ok(HttpStatus.OK.value(), "삭제에 성공하였습니다.");
    }

    @GetMapping("/{id}")
    public ApiResponse<VideoResDto> getVideos(@PathVariable("id") Long videosId) {
        VideoResDto videos = videosService.getVideos(videosId);
        return ApiResponse.ok(HttpStatus.OK.value(), "영상 조회에 성공하였습니다.", videos);
    }

    @GetMapping
    public ApiResponse<Page<VideosResponseDto>> getAllVideos(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                             @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        Page<VideosResponseDto> videosList = videosService.getAllVideos(page, size, sortBy, isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "목록 조회에 성공하였습니다.", videosList);
    }
}

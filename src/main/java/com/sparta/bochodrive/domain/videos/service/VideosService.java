package com.sparta.bochodrive.domain.videos.service;

import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.videos.dto.VideosRequestDto;
import com.sparta.bochodrive.domain.videos.dto.VideosResponseDto;
import org.springframework.data.domain.Page;

public interface VideosService {

    VideosResponseDto addVideos(VideosRequestDto videosRequestDto, User user);
    void deleteVideos(Long id, User user);
    Page<VideosResponseDto> getAllVideos(int page, int size, String sortBy, boolean isAsc);
    VideosResponseDto getVideos(Long id);
}

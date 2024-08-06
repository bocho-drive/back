package com.sparta.bochodrive.domain.videos.service;

import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.videos.dto.VideoResDto;
import com.sparta.bochodrive.domain.videos.dto.VideosRequestDto;
import com.sparta.bochodrive.domain.videos.dto.VideosResponseDto;
import com.sparta.bochodrive.domain.videos.entity.Videos;
import com.sparta.bochodrive.domain.videos.repository.VideosRepository;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.UnauthorizedException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class VideosServiceImpl implements VideosService {

    private final VideosRepository videosRepository;
    private final CommonFuntion commonFuntion;

    @Override
    @Transactional
    public VideosResponseDto addVideos(VideosRequestDto videosRequestDto, User user) {
        Videos videos = Videos.builder()
                .user(user)
                .title(videosRequestDto.getTitle())
                .url(videosRequestDto.getUrl())
                .build();

        return videosRepository.save(videos).toDto();
    }

    @Override
    @Transactional
    public void deleteVideos(Long id, User user) {
        commonFuntion.existsById(user.getId());
        Videos videos = videosRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        if (!videos.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorCode.DELETE_FAILED);
        } else {
            videosRepository.deleteById(id);
        }
    }

    @Override
    public Page<VideosResponseDto> getAllVideos(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Videos> videoList = videosRepository.findAllByOrderByCreatedAtDesc(pageable);

        return videoList.map(videos -> new VideosResponseDto(videos));
    }

    @Override
    public VideoResDto getVideos(Long id) {
        Videos videos = videosRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return new VideoResDto(videos);
    }
}

package com.sparta.bochodrive.domain.videos.dto;

import com.sparta.bochodrive.domain.videos.entity.Videos;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoResDto {

    private Long id;
    private String nickName;
    private Long userId;
    private String title;
    private String url;
    private LocalDateTime createdAt;

    public VideoResDto(Videos videos) {
        this.id = videos.getId();
        this.nickName = videos.getUser().getNickname();
        this.userId = videos.getUser().getId();
        this.title = videos.getTitle();
        this.url = videos.getUrl();
        this.createdAt = videos.getCreatedAt();
    }

}

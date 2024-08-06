package com.sparta.bochodrive.domain.videos.dto;

import com.sparta.bochodrive.domain.videos.entity.Videos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideosResponseDto {

    private Long id;
    private String nickName;
    private String title;
    private String url;
    private LocalDateTime createdAt;

    public VideosResponseDto(Videos videos) {
        this.id = videos.getId();
//        this.nickName = videos.getUser().getNickname();
        this.title = videos.getTitle();
        this.url = videos.getUrl();
//        this.createdAt = videos.getCreatedAt();
    }

}

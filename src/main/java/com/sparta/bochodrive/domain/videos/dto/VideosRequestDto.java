package com.sparta.bochodrive.domain.videos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideosRequestDto {

    private String userId;
    private String title;
    private String url;
}

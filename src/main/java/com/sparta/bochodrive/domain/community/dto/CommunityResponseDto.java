package com.sparta.bochodrive.domain.community.dto;

import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityResponseDto {

    private Long id;
    private String title;
    private String content;
    private CategoryEnum category;
    private String author;
    private int viewCount=0;
    private List<String> imageUploadUrls;
    private LocalDateTime createdAt;


    public CommunityResponseDto(Community saveCommunity) {
    }

    public void addViewCount() {
        this.viewCount++;
    }
}

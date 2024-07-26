package com.sparta.bochodrive.domain.community.dto;

import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private CategoryEnum category;
    private int viewCount;
    private LocalDateTime createdAt;
    //private File[] imgUrl;


    public CommunityResponseDto(Community saveCommunity) {
        this.id = saveCommunity.getId();
        this.title = saveCommunity.getTitle();
        this.content = saveCommunity.getContent();
        this.author=saveCommunity.getUser().getNickname();
        this.category = saveCommunity.getCategory();
        this.createdAt = saveCommunity.getCreatedAt();
        this.viewCount++;
    }
}

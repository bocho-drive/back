package com.sparta.bochodrive.domain.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
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
    private int likeCount;
    private LocalDateTime createdAt;

    @JsonProperty("isAuthor")
    private boolean isAuthor=false;

    private List<String> imgUrls;



    public CommunityResponseDto(Community saveCommunity,boolean isAuthor) {
        this.id = saveCommunity.getId();
        this.title = saveCommunity.getTitle();
        this.content = saveCommunity.getContent();
        this.author=saveCommunity.getUser().getNickname();
        this.category = saveCommunity.getCategory();
        this.createdAt = saveCommunity.getCreatedAt();
        this.viewCount= saveCommunity.getViewCount();
        this.likeCount=saveCommunity.getLikeCount();
        this.isAuthor=isAuthor;
        this.imgUrls=saveCommunity.getImages().stream().map(ImageS3::getUploadUrl).toList();
    }


    public CommunityResponseDto(ChallengeVarify challengeVarify,boolean isAuthor) {
        this.id=challengeVarify.getCommunity().getId();
        this.title=challengeVarify.getCommunity().getTitle();
        this.content=challengeVarify.getCommunity().getContent();
        this.author=challengeVarify.getUser().getNickname();
        this.category=challengeVarify.getCommunity().getCategory();
        this.createdAt = challengeVarify.getCreatedAt();
        this.viewCount=challengeVarify.getCommunity().getViewCount();
        this.likeCount=challengeVarify.getCommunity().getLikeCount();
        this.isAuthor=isAuthor;
        this.imgUrls=challengeVarify.getCommunity().getImages().stream().map(ImageS3::getUploadUrl).toList();
    }
}

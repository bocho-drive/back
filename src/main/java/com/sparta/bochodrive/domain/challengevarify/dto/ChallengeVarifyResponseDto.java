package com.sparta.bochodrive.domain.challengevarify.dto;

import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeVarifyResponseDto {

    private Long id;
    private Long challengeId;
    private String title;
    private String content;
    private CategoryEnum category;
    private String author;
    private int viewCount;
    private int likeCount;
    private List<String> imgUrls;
    private LocalDateTime createdAt;


    public ChallengeVarifyResponseDto(ChallengeVarify challengeVarifySaved) {
        this.id = challengeVarifySaved.getId();
        this.challengeId=challengeVarifySaved.getChallenge().getId();
        this.title=challengeVarifySaved.getCommunity().getTitle();
        this.content=challengeVarifySaved.getCommunity().getContent();
        this.category=challengeVarifySaved.getCommunity().getCategory();
        this.author=challengeVarifySaved.getUser().getNickname();
        this.viewCount=challengeVarifySaved.getCommunity().getViewCount();
        this.likeCount=challengeVarifySaved.getCommunity().getLikeCount();
        this.imgUrls=challengeVarifySaved.getCommunity().getImages().stream().map(ImageS3::getUploadUrl).toList();
        this.createdAt=challengeVarifySaved.getCreatedAt();
    }
}

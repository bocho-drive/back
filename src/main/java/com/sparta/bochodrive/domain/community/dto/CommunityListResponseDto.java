package com.sparta.bochodrive.domain.community.dto;

import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.community.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityListResponseDto {

    private Long id;
    private String title;
    private String author;
    private int viewCount;
    private int likeCount;
    private LocalDateTime createdAt;
    private boolean verifiedYN;

    public CommunityListResponseDto(Community community) {
        this.id=community.getId();
        this.title=community.getTitle();
        this.author=community.getUser().getNickname();
        this.createdAt=community.getCreatedAt();
        this.verifiedYN=community.isVerifiedYN();
        this.viewCount=community.getViewCount();
        this.likeCount=community.getLikeCount();
    }

    public CommunityListResponseDto(ChallengeVarify challengeVarify) {
        this.id=challengeVarify.getId();
        this.title=challengeVarify.getCommunity().getTitle();
        this.author=challengeVarify.getCommunity().getUser().getNickname();
        this.createdAt=challengeVarify.getCreatedAt();
        this.verifiedYN=challengeVarify.getCommunity().isVerifiedYN();
        this.viewCount=challengeVarify.getCommunity().getViewCount();
        this.likeCount=challengeVarify.getCommunity().getLikeCount();
    }
}

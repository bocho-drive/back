package com.sparta.bochodrive.domain.community.dto;

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

}

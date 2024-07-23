package com.sparta.bochodrive.domain.community.dto;

import com.sparta.bochodrive.domain.community.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityListResponseDto {

    private Long id;
    private String title;
    private int viewCount;
    private LocalDateTime createdAt;
    private boolean verifiedYN;

    public CommunityListResponseDto(Community community) {
        this.id=community.getId();
        this.title=community.getTitle();
        this.createdAt=community.getCreatedAt();
        this.verifiedYN=community.isVerifiedYN();

    }
}

package com.sparta.bochodrive.domain.challengevarify.dto;

import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeVarifyResponseDto {

    Long id;
    Long challengeId;
    Long userId;
    String title;
    String content;
    String author;
    Integer view_count;
    //String[] imageUploadUrls;
    LocalDateTime createdAt;


    public ChallengeVarifyResponseDto(ChallengeVarify challengeVarifySaved) {
        this.id = challengeVarifySaved.getId();
        this.challengeId=challengeVarifySaved.getChallenge().getId();
        this.userId=challengeVarifySaved.getCommunity().getUser().getId();
        this.title=challengeVarifySaved.getCommunity().getTitle();
        this.content=challengeVarifySaved.getCommunity().getContent();
    }
}

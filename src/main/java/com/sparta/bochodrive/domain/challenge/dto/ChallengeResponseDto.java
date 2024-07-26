package com.sparta.bochodrive.domain.challenge.dto;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public ChallengeResponseDto(Challenge savedChallenge) {
        this.id = savedChallenge.getId();
        this.title = savedChallenge.getTitle();
        this.content = savedChallenge.getContent();
        this.createdAt = savedChallenge.getCreatedAt();
    }
}

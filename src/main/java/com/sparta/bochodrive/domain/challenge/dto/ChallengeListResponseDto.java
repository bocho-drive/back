package com.sparta.bochodrive.domain.challenge.dto;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeListResponseDto {

    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public ChallengeListResponseDto(Challenge challenge) {
        this.id = challenge.getId();
        this.title = challenge.getTitle();
        this.createdAt = challenge.getCreatedAt();

    }
}

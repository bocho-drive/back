package com.sparta.bochodrive.domain.challengevarify.entity;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.challengevarify.dto.ChallengeVarifyRequestDto;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.global.entity.CreatedTimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="challenge_varifies")
public class ChallengeVarify extends CreatedTimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean completeYN;

    //게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id",nullable = false)
    private Community community;

    //챌린지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="challenge_id", nullable = false)
    private Challenge challenge;

    public ChallengeVarify(ChallengeVarifyRequestDto requestDto) {

        this.community.setTitle(requestDto.getTitle());
        this.community.setContent(requestDto.getContent());
        this.community.setCategory(requestDto.getCategory());
        this.community.getUser().setNickname(requestDto.getAuthor());

    }




}

package com.sparta.bochodrive.domain.challengevarify.entity;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.user.entity.User;
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


    //게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id",nullable = false)
    private Community community;

    //챌린지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="challenge_id", nullable = false)
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;


    public ChallengeVarify(User user, Community community, Challenge challenge) {
        this.community=community;
        this.user = user;
        this.challenge = challenge;
    }



}

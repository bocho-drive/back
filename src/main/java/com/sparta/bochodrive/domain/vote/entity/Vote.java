package com.sparta.bochodrive.domain.vote.entity;

import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.vote.dto.VoteRequestDto;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="votes")
public class Vote extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean agreeYn;

    //게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id",nullable = false)
    private Community community;


    //회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
}

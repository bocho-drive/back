package com.sparta.bochodrive.domain.vote.entity;


import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.vote.dto.VoteRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean agreeYN;


    //게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id",nullable = false)
    private Community community;


    //회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    public Vote(VoteRequestDto voteRequestDto) {
        this.community.setId(voteRequestDto.getCommunitesId());
        this.user.setId(voteRequestDto.getUserId());
        this.agreeYN = voteRequestDto.isAgreeYn();
    }
}

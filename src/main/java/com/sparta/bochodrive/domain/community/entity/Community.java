package com.sparta.bochodrive.domain.community.entity;

import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.community.dto.CommunityRequestDto;
import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
import com.sparta.bochodrive.domain.like.entity.Like;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.vote.entity.Vote;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="communities")
public class Community extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Column(nullable = false)
    @Size(min = 1)
    private String content;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private boolean verifiedYN;

    @Column(nullable = false)
    private boolean deleteYN;

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //댓글
    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    //챌린지 인증
    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<ChallengeVarify> challengeVarifies;

    //게시글 투표
    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    //좋아요
    @OneToMany(mappedBy = "community")
    private List<Like> likes;

    //이미지
    @OneToMany(mappedBy = "community", cascade = CascadeType.PERSIST)
    private List<ImageS3> images;

    @Builder
    public Community(String title, String content, CategoryEnum category, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
        this.viewCount = 0;
        this.likeCount = 0;
        this.verifiedYN = false;
        this.deleteYN = false;
    }

    public void update(CommunityRequestDto communityRequestDto) {
        this.title = communityRequestDto.getTitle();
        this.content = communityRequestDto.getContent();
        this.category = communityRequestDto.getCategory();
    }

    public void setDeleteYn(boolean b) {
        this.deleteYN = b;
    }
}

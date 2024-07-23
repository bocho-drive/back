package com.sparta.bochodrive.domain.comment.entity;

import com.sparta.bochodrive.domain.comment.dto.CommentRequestDto;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean deleteYN;

    //게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id",nullable = false)
    private Community community;

    //회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;



    public Comment(CommentRequestDto commentRequestDto, User user, Community community) {
        this.community=community;
        this.user = user;
        this.content= commentRequestDto.getContent();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content= commentRequestDto.getContent();
    }
}

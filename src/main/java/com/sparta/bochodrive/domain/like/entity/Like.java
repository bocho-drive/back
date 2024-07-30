package com.sparta.bochodrive.domain.like.entity;

import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.like.dto.LikeRequestDto;
import com.sparta.bochodrive.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id",nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public Like(User user, Community community) {
        this.community = community;
        this.user = user;
    }


}

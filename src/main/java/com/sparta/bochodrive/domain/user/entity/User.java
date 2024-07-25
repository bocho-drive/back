package com.sparta.bochodrive.domain.user.entity;


import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.vote.entity.Vote;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @Size(max = 255)
    private String email;

    @Column(nullable = false, length = 255)
    @Size(max = 255)
    private String password;

    @Column(nullable = false,length = 100)
    @Size(max = 100)
    private String nickname;

    @Column(nullable = false)
    private boolean deleteYN;

    //게시글
    @OneToMany(mappedBy = "user")
    private List<Community> communities;

    //댓글
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;







}

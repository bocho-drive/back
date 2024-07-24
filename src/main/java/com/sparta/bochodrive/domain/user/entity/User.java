package com.sparta.bochodrive.domain.user.entity;


import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
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
    private int id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
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

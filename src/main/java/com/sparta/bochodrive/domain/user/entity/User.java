package com.sparta.bochodrive.domain.user.entity;

import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.comment.entity.Comment;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.like.entity.Like;
import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.domain.vote.entity.Vote;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "DELETE_YN", nullable = false)
    private boolean deleteYN;

    @Column(name = "USER_ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<ChallengeVarify> challengeVarifies;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<Like> likes;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<Comment> comments;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<Community> communities;


    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<Vote> votes;

    public static User of(UserModel.UserRegistDto userRegistDto, BCryptPasswordEncoder encoder) {
        return User.builder()
                .email(userRegistDto.getEmail())
                .password(encoder.encode(userRegistDto.getPassword()))
                .nickname(userRegistDto.getNickname())
                .deleteYN(false)
                .userRole(UserRole.USER)
                .build();
    }

    public UserModel.UserResponseDto toDto() {
        return UserModel.UserResponseDto.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .build();
    }
}
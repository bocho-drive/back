package com.sparta.bochodrive.domain.challenge.entity;


import com.sparta.bochodrive.domain.challenge.dto.ChallengeRequestDto;
import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="challenges")
public class Challenge extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    @Size(min = 1, max = 100)
    private String title;

    @Column(nullable = false)
    private String content;





    //챌린지 인증
    @OneToMany(mappedBy = "challenge",cascade = CascadeType.REMOVE)
    private List<ChallengeVarify> challengeVarifies;


    public Challenge(ChallengeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void update(ChallengeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();

    }
}

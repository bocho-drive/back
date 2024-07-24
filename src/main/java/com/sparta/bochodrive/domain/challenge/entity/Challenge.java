package com.sparta.bochodrive.domain.challenge.entity;


import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;



    //챌린지 인증
    @OneToMany(mappedBy = "challenge")
    private List<ChallengeVarify> challengeVarifies;





}

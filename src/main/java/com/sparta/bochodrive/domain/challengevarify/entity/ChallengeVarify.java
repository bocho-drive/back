package com.sparta.bochodrive.domain.challengevarify.entity;

import com.sparta.bochodrive.global.entity.CreatedTimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeVarify extends CreatedTimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean completeYN;



//    //내일 물어보기
//    @Transient
//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private LocalDateTime updatedAt=null;

}

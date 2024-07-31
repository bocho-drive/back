package com.sparta.bochodrive.domain.community.entity;


import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Community extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private CategoryEnum category;

    @Column(nullable = false)
    private String content;


    @Column(nullable = false)
    private boolean verifiedYN=false;


    @Column(nullable = false)
    private boolean deleteYN=false;






}

package com.sparta.bochodrive.domain.imageS3.entity;

import jakarta.persistence.*;

@Entity
public class ImageS3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uploadUrl;
}

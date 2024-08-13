package com.sparta.bochodrive.domain.imageS3.entity;

import com.sparta.bochodrive.domain.community.entity.Community;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageS3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uploadUrl;

    @Column(nullable = false)
    private String fileName;

    //게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id",nullable = false)
    private Community community;
}

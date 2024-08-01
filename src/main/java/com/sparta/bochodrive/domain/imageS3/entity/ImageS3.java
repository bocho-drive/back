package com.sparta.bochodrive.domain.imageS3.entity;

import com.sparta.bochodrive.domain.community.entity.Community;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageS3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uploadUrl;

    //게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="community_id",nullable = false)
    private Community community;

    public ImageS3( String url, Community community) {

        this.uploadUrl = url;
        this.community = community;
    }
}

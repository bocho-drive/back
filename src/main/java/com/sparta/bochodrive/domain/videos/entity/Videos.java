package com.sparta.bochodrive.domain.videos.entity;


import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.videos.dto.VideosResponseDto;
import com.sparta.bochodrive.global.entity.CreatedTimeStamped;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Videos extends CreatedTimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    public VideosResponseDto toDto() {
        return VideosResponseDto.builder()
                .id(this.id)
                .nickName(this.user.getNickname())
                .title(this.title)
                .url(this.url)
                .build();

    }

}

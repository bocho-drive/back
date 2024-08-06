package com.sparta.bochodrive.domain.mypage.dto;


import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageCommunityListResponseDto {

    private CommunityRepository communityRepository;

    private Long id;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private Page<CommunityListResponseDto> communityListResponseDto;

    public MypageCommunityListResponseDto (User user, Page<Community> communities) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.communityListResponseDto = communities.map(CommunityListResponseDto::new) ;
    }

}

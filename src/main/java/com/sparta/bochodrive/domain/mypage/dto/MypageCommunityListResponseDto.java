package com.sparta.bochodrive.domain.mypage.dto;


import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class MypageCommunityListResponseDto {

    private CategoryEnum category;
    private Page<CommunityListResponseDto> communityListResponseDto;

    public MypageCommunityListResponseDto(CategoryEnum category, Page<Community> communities) {
        this.category = category;
        this.communityListResponseDto = communities.map(CommunityListResponseDto::new);
    }
}

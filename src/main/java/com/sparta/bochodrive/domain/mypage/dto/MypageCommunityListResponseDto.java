package com.sparta.bochodrive.domain.mypage.dto;


import com.sparta.bochodrive.domain.community.dto.CommunityListResponseDto;
import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class MypageCommunityListResponseDto extends CommunityListResponseDto {

    private CategoryEnum category;

    public MypageCommunityListResponseDto(Community communities) {
        super(communities);
        this.category = communities.getCategory();
    }
}

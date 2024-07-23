package com.sparta.bochodrive.domain.community.dto;


import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRequestDto {


    private String title;
    private String content;
    private CategoryEnum category;
    private List<String> images;


}

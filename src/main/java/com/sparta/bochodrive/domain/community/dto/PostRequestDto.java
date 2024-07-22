package com.sparta.bochodrive.domain.community.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {


    private String title;
    private String content;
    private String category;
    private boolean isVote;
    private List<String> images;


}

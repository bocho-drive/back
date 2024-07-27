package com.sparta.bochodrive.domain.challengevarify.dto;

import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeVarifyRequestDto {

    private String author;
    private String title;
    private String content;
    private CategoryEnum category;
    //private File[] imgUrl;
}

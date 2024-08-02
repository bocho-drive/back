package com.sparta.bochodrive.domain.challengevarify.dto;

import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter //form-data는 setter는 있어야해서
public class ChallengeVarifyRequestDto {

    private String title;
    private String content;
    private CategoryEnum category;
    private List<MultipartFile> image;

}
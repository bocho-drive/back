package com.sparta.bochodrive.domain.community.dto;


import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CommunityRequestDto {

    private String title;
    private String content;
    private CategoryEnum category;
    private List<MultipartFile> image;
}

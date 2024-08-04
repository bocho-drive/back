package com.sparta.bochodrive.domain.imageS3.controller;


import com.sparta.bochodrive.domain.imageS3.dto.ImageS3DeleteRequestDto;
import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
import com.sparta.bochodrive.domain.imageS3.service.ImageS3Service;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageS3Controller {

    private final ImageS3Service imageS3Service;

    //url 요청시 이미지 삭제
    @DeleteMapping
    public ApiResponse deleteFiles(@RequestBody ImageS3DeleteRequestDto requestDto){
        imageS3Service.deleteFile(requestDto);
        return ApiResponse.ok(HttpStatus.OK.value(), "이미지 삭제에 성공하셨습니다.");
    }

}

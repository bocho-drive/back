package com.sparta.bochodrive.domain.imageS3.controller;


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

    //S3 테스트를 위한 컨트롤러입니다!!!!!


    //이미지 삽입
    @PostMapping
    public ApiResponse<String> uploadFiles(@RequestParam("files") MultipartFile files) throws IOException {

        String url=imageS3Service.upload(files);
       return ApiResponse.ok(HttpStatus.OK.value(), "업로드 성공하셨습니다.", url);
    }


    //이미지 삭제
    @DeleteMapping("{id}")
    public ApiResponse deleteFile(@PathVariable("id") Long id) throws IOException {
        imageS3Service.deleteFile(id);
        return ApiResponse.ok(HttpStatus.OK.value(), "이미지 삭제 성공하셨습니다.");
    }

}

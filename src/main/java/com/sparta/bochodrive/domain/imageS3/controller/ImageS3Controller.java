package com.sparta.bochodrive.domain.imageS3.controller;


import com.sparta.bochodrive.domain.imageS3.dto.ImageS3DeleteRequestDto;
import com.sparta.bochodrive.domain.imageS3.service.ImageS3Service;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

package com.sparta.bochodrive.domain.imageS3.controller;


import com.sparta.bochodrive.domain.imageS3.service.S3Service;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;


    //파일 업로드
    @PostMapping("/upload")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(),"업로드할 파일이 존재하지 않습니다.");
        }
        try {
            String fileUrl = s3Service.uploadImage(file);
            return ApiResponse.ok(HttpStatus.OK.value(),"파일 업로드에 성공하셨습니다.",fileUrl);
        } catch (IOException e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 업로드에 실패하셨습니다.");
        }
    }
    //파일 수정



    //파일 삭제
    @DeleteMapping
    public ApiResponse<String> deleteFile(@RequestParam("file") MultipartFile file) {
        s3Service.deleteImage(file);
        try{
            return ApiResponse.ok(HttpStatus.OK.value(), "파일 삭제에 성공하셨습니다.");
        }catch (Exception e){
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 삭제에 실패하셨습니다.");
        }
    }
}

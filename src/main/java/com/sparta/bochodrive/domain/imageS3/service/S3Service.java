package com.sparta.bochodrive.domain.imageS3.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.amazonaws.services.s3.model.ObjectMetadata;

import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //파일 업로드
    public String uploadImage(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new NotFoundException(ErrorCode.FILE_NOT_FOUND);
        }

        String s3Filename = file.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getInputStream().available());
        s3Client.putObject(bucket, s3Filename, file.getInputStream(), objectMetadata);
        return URLDecoder.decode(s3Client.getUrl(bucket, s3Filename).toString(), StandardCharsets.UTF_8);
    }

    //파일 수정


    //파일 삭제
    public void deleteImage(MultipartFile file) {
        s3Client.deleteObject(bucket, file.getOriginalFilename());
    }
}

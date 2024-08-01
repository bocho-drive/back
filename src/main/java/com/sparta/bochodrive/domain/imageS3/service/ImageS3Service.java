package com.sparta.bochodrive.domain.imageS3.service;
import com.amazonaws.services.cloudformation.model.Change;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
import com.sparta.bochodrive.domain.imageS3.repository.ImageS3Repository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImageS3Service {

    private final AmazonS3Client amazonS3Client;
    private final ImageS3Repository imageS3Repository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile file) throws IOException {
       File uploadFile = convert(file).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));
        String uploadedUrl = upload(uploadFile);
        return uploadedUrl;
    }

    private String upload(File uploadFile) {
        String filename = UUID.randomUUID().toString();
        log.info(filename);
        String uploadImageUrl = putS3(uploadFile, filename);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }
    public String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }



    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 로컬에서 삭제되었습니다.");
            return;
        }
        log.info("파일을 로컬에서 삭제하는데 실패하였습니다.");
    }

    //s3로 업로드 요청
    private String putS3(File uploadFile,String filename){
        amazonS3Client.putObject(new PutObjectRequest(bucket, filename, uploadFile));
        return amazonS3Client.getUrl(bucket,filename).toString();
    }

    //MultipartFile을 File 형태로 변환
    private Optional<File> convert(MultipartFile file) throws IOException {
        //파일 생성
        File convertFile=new File(System.getProperty("user.dir")+"/"+file.getOriginalFilename());

        if(convertFile.createNewFile()){
            try(FileOutputStream fos=new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    //s3에 파일 삭제 요청
    public void deleteFile(String fileName) {
        log.info("삭제할 파일 이름 : {}", fileName);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    //s3에 파일 삭제 요청
    public void deleteFile(Long id) {
        Optional<ImageS3> imageS3Optional = imageS3Repository.findById(id);
        String fileName = imageS3Optional.get().getFileName();
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }




}

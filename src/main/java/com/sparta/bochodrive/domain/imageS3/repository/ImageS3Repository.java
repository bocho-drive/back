package com.sparta.bochodrive.domain.imageS3.repository;

import com.sparta.bochodrive.domain.imageS3.entity.ImageS3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Component
public interface ImageS3Repository extends JpaRepository<ImageS3,Long> {

    List<ImageS3> findAllByCommunityId(Long id);

    ImageS3 findByFileName(String fileName);
}

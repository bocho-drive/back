package com.sparta.bochodrive.domain.videos.repository;

import com.sparta.bochodrive.domain.videos.entity.Videos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideosRepository extends JpaRepository<Videos, Long> {
    Page<Videos> findAllByOrderByCreatedAtDesc(Pageable pageable);


}

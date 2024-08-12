package com.sparta.bochodrive.domain.drivematching.repository;

import com.sparta.bochodrive.domain.drivematching.entity.DriveMatching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriveMatchingRepository extends JpaRepository<DriveMatching, Long> {

    Page<DriveMatching> findAllByOrderByCreatedAt(Pageable pageable);

    @Query("select d from DriveMatching d where d.deleteYN = false order by d.createdAt desc")
    Page<DriveMatching> findAllByDeleteYNFalseAndOrderByCreatedAtDesc(Pageable pageable);

    Page<DriveMatching> findAllByUserId(Long userId, Pageable pageable);
}

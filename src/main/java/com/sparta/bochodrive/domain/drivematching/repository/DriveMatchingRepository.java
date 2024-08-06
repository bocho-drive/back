package com.sparta.bochodrive.domain.drivematching.repository;

import com.sparta.bochodrive.domain.drivematching.entity.DriveMatching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveMatchingRepository extends JpaRepository<DriveMatching, Long> {

    Page<DriveMatching> findAllByOrderByCreatedAt(Pageable pageable);
}

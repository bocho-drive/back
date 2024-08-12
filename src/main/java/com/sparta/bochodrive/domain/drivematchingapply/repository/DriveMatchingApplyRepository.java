package com.sparta.bochodrive.domain.drivematchingapply.repository;


import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriveMatchingApplyRepository extends JpaRepository<DriveMatchingApply, Long> {

    List<DriveMatchingApply> findDriveMatchingApplyByDriveMatchingIdAndDeleteYnFalse(Long driveMatchingId);

    Optional<DriveMatchingApply> findByDriveMatchingId(Long driveMatchingId);

    Optional<DriveMatchingApply> findByDriveMatchingIdAndUserIdAndDeleteYnFalse(Long driveMatchingId, Long userId);



//    DriveMatchingApply[] findAllByTeachers(DriveMatchingApplyRequestDto driveMatchingApplyRequestDto);

}

package com.sparta.bochodrive.domain.drivematching.service;

import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingDetailResponseDto;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingRequestDto;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingResponseVo;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingStatusRequestDto;
import com.sparta.bochodrive.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface DriveMatchingService {

    DriveMatchingResponseVo addDriveMatching(DriveMatchingRequestDto driveMatchingRequestDto, User user);
    Page<DriveMatchingResponseVo> getAllDriveMatching(int page, int size, String sortBy, boolean isAsc);
    DriveMatchingDetailResponseDto getDriveMatching(Long id);


    void updateDriveMatching(Long id, DriveMatchingRequestDto driveMatchingRequestDto, User user);
    void updateDriveMatchingStatus(Long id, DriveMatchingStatusRequestDto matchingStatusRequestDto, User user);
    void deleteDriveMatching(Long id, User user);

}

package com.sparta.bochodrive.domain.drivematchingapply.service;


import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyRequestDto;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;
import org.springframework.data.domain.Page;

public interface DriveMatchingApplyService {

    Page<DriveMatchingApplyResponseDto> findDriveMatchingApplys(Long driveMatchingId, Long userId, int page, int size, String sortBy, boolean isAsc);
    void addDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto);
    void deleteDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto);
}

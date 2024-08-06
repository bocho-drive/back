package com.sparta.bochodrive.domain.drivematchingapply.service;


import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyRequestDto;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;

import java.util.List;

public interface DriveMatchingApplyService {

    List<DriveMatchingApplyResponseDto> findDriveMatchingApplys(Long driveMatchingId);
    void addDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto);
    void deleteDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto);
}

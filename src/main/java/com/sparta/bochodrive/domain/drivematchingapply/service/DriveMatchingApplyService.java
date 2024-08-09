package com.sparta.bochodrive.domain.drivematchingapply.service;


import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyRequestDto;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.user.entity.User;

import java.util.List;

public interface DriveMatchingApplyService {

    List<DriveMatchingApplyResponseDto> findDriveMatchingApplys(Long driveMatchingId);
    void addDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto);
    void deleteDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto);
    boolean validPermission(Long id, User user);
    DriveMatchingApply getDriveMatchingByMatchingId(Long id);
    DriveMatchingApply getDriveMatchingApplyById(Long id);
}

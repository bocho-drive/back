package com.sparta.bochodrive.domain.drivematchingapply.service;

import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriveMatchingApplyServiceImplTest {

    private final DriveMatchingApplyRequestDto;

    @Test
    void findDriveMatchingApplys() {
    }

    @Test
    void addDriveMatchingApply() {
        DriveMatchingApplyRequestDto driveMatchingApplyRequestDto = DriveMatchingApplyRequestDto.builder()
                .driveMatchingId(1L)
                .userId(1L)
                .build();



    }

    @Test
    void deleteDriveMatchingApply() {
    }
}
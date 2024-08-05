//package com.sparta.bochodrive.domain.drivematchingapply.service;
//
//import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyRequestDto;
//import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;
//import org.hibernate.service.spi.InjectService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DriveMatchingApplyServiceImplTest {
//
//    @Autowired
//    private DriveMatchingApplyServiceImpl driveMatchingApplyServiceImpl;
//
//    @Test
//    void findDriveMatchingApplys() {
//        DriveMatchingApplyResponseDto[] driveMatchingApplys = driveMatchingApplyServiceImpl.findDriveMatchingApplys(1L, 1L);
//
//        System.out.println(Arrays.toString(driveMatchingApplys));
//
//    }
//
//    @Test
//    void addDriveMatchingApply() {
//        DriveMatchingApplyRequestDto driveMatchingApplyRequestDto = DriveMatchingApplyRequestDto.builder()
//                .driveMatchingId(1L)
//                .userId(1L)
//                .build();
//
//
//
//    }
//
//    @Test
//    void deleteDriveMatchingApply() {
//    }
//
//}
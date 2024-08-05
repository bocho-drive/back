//package com.sparta.bochodrive.domain.drivematching.service;
//
//import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingDetailResponseDto;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class DriveMatchingServiceImplTest {
//
//    private static final Logger log = LoggerFactory.getLogger(DriveMatchingServiceImplTest.class);
//    @Autowired
//    private DriveMatchingService driveMatchingService;
//
//    @Test
//    void getDriveMatching() {
//        DriveMatchingDetailResponseDto driveMatching = driveMatchingService.getDriveMatching(1L);
//
//        assertNotNull(driveMatching);
//        log.info("driveMatching: {}", driveMatching.getUserId());
//    }
//}
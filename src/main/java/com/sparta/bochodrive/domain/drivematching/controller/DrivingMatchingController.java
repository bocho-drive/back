package com.sparta.bochodrive.domain.drivematching.controller;

import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingDetailResponseDto;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingRequestDto;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingResponseVo;
import com.sparta.bochodrive.domain.drivematching.service.DriveMatchingService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class DrivingMatchingController {
    private final DriveMatchingService driveMatchingService;

    @GetMapping("/drive_matchings")
    public ApiResponse<Page<DriveMatchingResponseVo>> getAllDriveMatching(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                                                          @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                          @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {
        Page<DriveMatchingResponseVo> driveMatchingList = driveMatchingService.getAllDriveMatching(page, size, sortBy, isAsc);

        return ApiResponse.ok(HttpStatus.OK.value(), "목록 조회에 성공하였습니다.", driveMatchingList);
    }

    @GetMapping("/drive_matchings/{id}")
    public ApiResponse<DriveMatchingDetailResponseDto> getDriveMatching(@PathVariable("id") Long id) {
        DriveMatchingDetailResponseDto driveMatching = driveMatchingService.getDriveMatching(id);

        return ApiResponse.ok(HttpStatus.OK.value(), "조회에 성공하였습니다..", driveMatching);
    }

    @PostMapping("/api/drive_matchings")
    public ApiResponse<DriveMatchingResponseVo> addDrivingMatching(@RequestBody DriveMatchingRequestDto driveMatchingRequestDto,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        DriveMatchingResponseVo result = driveMatchingService.addDriveMatching(driveMatchingRequestDto, userDetails.getUser());
        return ApiResponse.ok(HttpStatus.CREATED.value(), "매칭글 등록에 성공하셨습니다.", result);
    }

    @PutMapping("/api/drive_matchings/{id}")
    public ApiResponse updateDriveMatching(@PathVariable("id") Long id,
                                           @RequestBody DriveMatchingRequestDto driveMatchingRequestDto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        driveMatchingService.updateDriveMatching(id, driveMatchingRequestDto, userDetails.getUser());

        return ApiResponse.ok(HttpStatus.OK.value(), "수정에 성공하였습니다.");
    }

    @DeleteMapping("/api/drive_matchings/{id}")
    public ApiResponse deleteDriveMatching(@PathVariable("id") Long id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        driveMatchingService.deleteDriveMatching(id,  userDetails.getUser());

        return ApiResponse.ok(HttpStatus.OK.value(), "삭제에 성공하였습니다.");
    }


}

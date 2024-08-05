package com.sparta.bochodrive.domain.drivematchingapply.controller;

import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyRequestDto;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;
import com.sparta.bochodrive.domain.drivematchingapply.service.DriveMatchingApplyService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drive_matchings_applys")
@RequiredArgsConstructor
@Slf4j
public class DriveMatchingApplyController {

    private final DriveMatchingApplyService driveMatchingApplyService;

    @GetMapping
    public ApiResponse<Page<DriveMatchingApplyResponseDto>> getDriveMatchingApplys(
            @RequestParam(value = "drive_matchings_id", required = false) Long driveMatchingId,
            @RequestParam(value = "usersId", required = false) Long usersId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc) {

        Page<DriveMatchingApplyResponseDto> results = driveMatchingApplyService.findDriveMatchingApplys(driveMatchingId, usersId, page, size, sortBy, isAsc);
        return ApiResponse.ok(HttpStatus.OK.value(), "목록 조회에 성공하였습니다.", results);

    }

    @PostMapping("/{id}")
    public ApiResponse addDrivingMatchingApply(@PathVariable("id") Long id,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        driveMatchingApplyService.addDriveMatchingApply(id, DriveMatchingApplyRequestDto.builder().userId(userDetails.getUserId()).build());
        return ApiResponse.ok(HttpStatus.OK.value(), "지원에 성공하였습니다.");
    }
    @DeleteMapping("/{id}")
    public ApiResponse deleteDrivingMatchingApply(@PathVariable("id") Long id,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        driveMatchingApplyService.deleteDriveMatchingApply(id, DriveMatchingApplyRequestDto.builder().userId(userDetails.getUserId()).build());
        return ApiResponse.ok(HttpStatus.OK.value(), "지원삭제에 성공하였습니다.");
    }
}

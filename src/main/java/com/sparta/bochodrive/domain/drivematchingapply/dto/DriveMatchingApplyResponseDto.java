package com.sparta.bochodrive.domain.drivematchingapply.dto;

import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriveMatchingApplyResponseDto {
    private Long id;
    private Long driveMatchingId;
    private Long userId;
    private String nickname;

    public DriveMatchingApplyResponseDto(DriveMatchingApply driveMatchingApply) {
        this.id = driveMatchingApply.getId();
        this.driveMatchingId = driveMatchingApply.getDriveMatching().getId();
        this.userId = driveMatchingApply.getUser().getId();
    }
}

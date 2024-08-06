package com.sparta.bochodrive.domain.drivematchingapply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriveMatchingApplyRequestDto {

    private Long driveMatchingId;
    private Long userId;
}

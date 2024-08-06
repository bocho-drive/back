package com.sparta.bochodrive.domain.drivematching.dto;

import com.sparta.bochodrive.domain.drivematching.entity.Status;
import com.sparta.bochodrive.domain.drivematching.entity.Type;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class DriveMatchingDetailResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private Long studentId;
    private String studentName;
    private Long teacherId;
    private String content;
    private Type type;
    private Status status;
    private LocalDateTime createdAt;
}

package com.sparta.bochodrive.domain.drivematching.dto;

import com.sparta.bochodrive.domain.drivematching.entity.DriveMatching;
import com.sparta.bochodrive.domain.drivematching.entity.Status;
import com.sparta.bochodrive.domain.drivematching.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriveMatchingResponseVo {

    private Long id;
    private String title;
    private String content;
    private Type type;
    private Status status;
    private LocalDateTime createdAt;

    public DriveMatchingResponseVo(DriveMatching driveMatching) {
        this.id = driveMatching.getId();
        this.title = driveMatching.getTitle();
        this.content = driveMatching.getContent();
        this.type = driveMatching.getType();
        this.status = driveMatching.getStatus();
        this.createdAt = driveMatching.getCreatedAt();
    }
}

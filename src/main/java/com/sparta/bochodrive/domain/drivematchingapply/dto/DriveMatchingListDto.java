package com.sparta.bochodrive.domain.drivematchingapply.dto;

import com.sparta.bochodrive.domain.drivematching.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriveMatchingListDto {

    private Long matchingId;
    private String title;
    private String content;
    private Status status;
    private Boolean deleteYn;

    private Long applyId;
    private Long teacherId;
    private String teacherNickname;


}

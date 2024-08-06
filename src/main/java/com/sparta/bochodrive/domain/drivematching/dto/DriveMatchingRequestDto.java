package com.sparta.bochodrive.domain.drivematching.dto;

import com.sparta.bochodrive.domain.drivematching.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriveMatchingRequestDto {

    private String title;
    private String content;
    private Type type;
//    private Status status;
//    private boolean deleteYN;
}

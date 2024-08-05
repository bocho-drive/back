package com.sparta.bochodrive.domain.drivematchingapply.entity;


import com.sparta.bochodrive.domain.drivematching.entity.DriveMatching;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;
import com.sparta.bochodrive.domain.teacher.entity.Teachers;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriveMatchingApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_matching_id")
    @Setter
    private DriveMatching driveMatching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private Teachers teachers;

    public DriveMatchingApplyResponseDto toDto() {
        return DriveMatchingApplyResponseDto.builder()
                .id(this.id)
                .driveMatchingId(driveMatching.getId())
                .userId(teachers.getUserId())
                .build();
    }
}

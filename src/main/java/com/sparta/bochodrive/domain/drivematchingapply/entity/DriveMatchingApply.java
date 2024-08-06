package com.sparta.bochodrive.domain.drivematchingapply.entity;


import com.sparta.bochodrive.domain.drivematching.entity.DriveMatching;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;
import com.sparta.bochodrive.domain.user.entity.User;
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
    private User user;

    public DriveMatchingApplyResponseDto toDto() {
        return DriveMatchingApplyResponseDto.builder()
                .id(this.id)
                .driveMatchingId(driveMatching.getId())
                .userId(user.getId())
                .build();
    }
}

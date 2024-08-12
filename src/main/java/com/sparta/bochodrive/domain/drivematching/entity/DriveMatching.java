package com.sparta.bochodrive.domain.drivematching.entity;

import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingRequestDto;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingResponseVo;
import com.sparta.bochodrive.domain.teacher.entity.Teachers;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DriveMatching extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    @Setter
    private Teachers teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Boolean deleteYN;

    public DriveMatchingResponseVo toDto() {
        return DriveMatchingResponseVo.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .type(this.type)
                .status(this.status)
                .build();
    }

    public void update(DriveMatchingRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void matching(Teachers teacher) {
        this.teacher = teacher;
        this.updateStatus(Status.PROGRESS);
    }

    public void updateStatus(Status status) {
        this.status = status;

        if(status.equals(Status.CANCEL)) {
            this.teacher = null;
        }
    }

    public void delete() {
        this.deleteYN = true;
    }

}

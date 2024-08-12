package com.sparta.bochodrive.domain.drivematching.service;

import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingDetailResponseDto;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingRequestDto;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingResponseVo;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingStatusRequestDto;
import com.sparta.bochodrive.domain.drivematching.entity.DriveMatching;
import com.sparta.bochodrive.domain.drivematching.entity.Status;
import com.sparta.bochodrive.domain.drivematching.entity.Type;
import com.sparta.bochodrive.domain.drivematching.repository.DriveMatchingRepository;
import com.sparta.bochodrive.domain.teacher.entity.Teachers;
import com.sparta.bochodrive.domain.teacher.repository.TeachersRepository;
import com.sparta.bochodrive.domain.teacher.service.TeacherService;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.service.UserService;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DriveMatchingServiceImpl implements DriveMatchingService{

    private final DriveMatchingRepository driveMatchingRepository;
    private final TeacherService teacherService;


    @Override
    @Transactional
    public DriveMatchingResponseVo addDriveMatching(DriveMatchingRequestDto driveMatchingRequestDto, User user) {

        DriveMatching driveMatching = DriveMatching.builder()
                .title(driveMatchingRequestDto.getTitle())
                .content(driveMatchingRequestDto.getContent())
                .type(driveMatchingRequestDto.getType())
                .status(Status.WAITING)
                .deleteYN(false)
                .build();
        if(Type.TEACHER.equals(driveMatchingRequestDto.getType())) {
            throw new IllegalArgumentException(ErrorCode.DRIVE_MATCHING_ONLY_STUDENT.getMessage());
        }

        driveMatching.setUser(user);
        return driveMatchingRepository.save(driveMatching).toDto();
    }

    @Override
    public Page<DriveMatchingResponseVo> getAllDriveMatching(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<DriveMatching> driveMatchingList = driveMatchingRepository.findAllByDeleteYNFalseAndOrderByCreatedAtDesc(pageable);

        return driveMatchingList.map(DriveMatchingResponseVo::new);
    }

    @Override
    public DriveMatchingDetailResponseDto getDriveMatching(Long id) {
        DriveMatching driveMatching = driveMatchingRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return DriveMatchingDetailResponseDto.builder()
                .id(driveMatching.getId())
                .title(driveMatching.getTitle())
                .userId(driveMatching.getUser().getId())
                .studentId(driveMatching.getUser().getId())
                .studentName(driveMatching.getUser().getNickname())
                .teacherId(driveMatching.getTeacher() != null ? driveMatching.getTeacher().getId() : null)
                .teacherUserId(driveMatching.getTeacher() != null ? driveMatching.getTeacher().getUserId() : null)
                .content(driveMatching.getContent())
                .type(driveMatching.getType())
                .status(driveMatching.getStatus())
                .createdAt(driveMatching.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public void updateDriveMatching(Long id, DriveMatchingRequestDto driveMatchingRequestDto, User user) {

        DriveMatching driveMatching = driveMatchingRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        if(!Objects.equals(driveMatching.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("해당 게시글을 수정할 권한이 없습니다.");
        }
        driveMatching.update(driveMatchingRequestDto);
    }

    @Override
    @Transactional
    public void updateDriveMatchingStatus(Long id, DriveMatchingStatusRequestDto dto, User user) {
        DriveMatching driveMatching = driveMatchingRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        if(!Objects.equals(driveMatching.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("해당 게시글을 수정할 권한이 없습니다.");
        }
        if(dto.getStatus() == Status.PROGRESS) {
            driveMatching.matching(teacherService.findByUserId(dto.getApplyUserId()));
        } else {
            driveMatching.updateStatus(dto.getStatus());
        }
    }

    @Override
    @Transactional
    public void deleteDriveMatching(Long id, User user) {
        DriveMatching driveMatching = driveMatchingRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        if(!Objects.equals(driveMatching.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("해당 게시글을 삭제할 권한이 없습니다.");
        }
        driveMatching.delete();
        driveMatchingRepository.save(driveMatching);
    }
}

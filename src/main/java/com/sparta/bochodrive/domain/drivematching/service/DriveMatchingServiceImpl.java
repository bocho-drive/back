package com.sparta.bochodrive.domain.drivematching.service;

import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingRequestDto;
import com.sparta.bochodrive.domain.drivematching.dto.DriveMatchingResponseVo;
import com.sparta.bochodrive.domain.drivematching.entity.DriveMatching;
import com.sparta.bochodrive.domain.drivematching.entity.Status;
import com.sparta.bochodrive.domain.drivematching.entity.Type;
import com.sparta.bochodrive.domain.drivematching.repository.DriveMatchingRepository;
import com.sparta.bochodrive.domain.teacher.entity.Teachers;
import com.sparta.bochodrive.domain.teacher.repository.TeachersRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.videos.dto.VideosResponseDto;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DriveMatchingServiceImpl implements DriveMatchingService{

    private final DriveMatchingRepository driveMatchingRepository;
    private final TeachersRepository teachersRepository;


    @Override
    @Transactional
    public DriveMatchingResponseVo addDriveMatching(DriveMatchingRequestDto driveMatchingRequestDto, User user) {

        DriveMatching driveMatching = DriveMatching.builder()
                .title(driveMatchingRequestDto.getTitle())
                .content(driveMatchingRequestDto.getContent())
                .type(driveMatchingRequestDto.getType())
                .status(Status.WAITING)
                .build();
        if (Type.TEACHER.equals(driveMatchingRequestDto.getType())) {
            Teachers teachers = teachersRepository.findByUserId(user.getId());
            driveMatching.setTeacher(teachers);
        } else {
            driveMatching.setUser(user);
        }
        return driveMatchingRepository.save(driveMatching).toDto();
    }

    @Override
    public Page<DriveMatchingResponseVo> getAllDriveMatching(int page, int size, String sortBy, boolean isAsc) {
        size = 10;
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<DriveMatching> driveMatchingList = driveMatchingRepository.findAllByOrderByCreatedAt(pageable);

        return driveMatchingList.map(driveMatching -> new DriveMatchingResponseVo(driveMatching));
    }

    @Override
    public DriveMatchingResponseVo getDriveMatching(Long id) {
        DriveMatching driveMatching = driveMatchingRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        return new DriveMatchingResponseVo(driveMatching);
    }

    @Override
    @Transactional
    public void updateDriveMatching(Long id, DriveMatchingRequestDto driveMatchingRequestDto, User user) {
        DriveMatching driveMatching = driveMatchingRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        driveMatching.update(driveMatchingRequestDto);
    }

    @Override
    @Transactional
    public void deleteDriveMatching(Long id, User user) {
        driveMatchingRepository.deleteById(id);
    }
}

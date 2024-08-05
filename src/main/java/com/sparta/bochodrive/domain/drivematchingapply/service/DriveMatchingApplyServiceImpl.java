package com.sparta.bochodrive.domain.drivematchingapply.service;

import com.sparta.bochodrive.domain.drivematching.repository.DriveMatchingRepository;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyRequestDto;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.repository.DriveMatchingApplyRepository;
import com.sparta.bochodrive.domain.teacher.repository.TeachersRepository;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriveMatchingApplyServiceImpl implements DriveMatchingApplyService{

    private final DriveMatchingApplyRepository driveMatchingApplyRepository;
    private final DriveMatchingRepository driveMatchingRepository;
    private final TeachersRepository teachersRepository;

    @Override
    public Page<DriveMatchingApplyResponseDto> findDriveMatchingApplys(Long driveMatchingId, Long userId, int page, int size, String sortBy, boolean isAsc) {
        size = 10;
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<DriveMatchingApply> resultList;
        DriveMatchingApplyRequestDto request = DriveMatchingApplyRequestDto.builder()
                .driveMatchingId(driveMatchingId)
                .userId(userId)
                .build();

        if (driveMatchingId != null) {
            resultList = driveMatchingApplyRepository.findAllByDriveMatchingOrderByCreatedAt(request, pageable);
        } else {
            resultList = driveMatchingApplyRepository.findAllByTeachersOrderByCreatedAt(request, pageable);
        }

        return resultList.map((entity) -> new DriveMatchingApplyResponseDto(entity));
    }

    @Override
    public void addDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto) {
        DriveMatchingApply apply = DriveMatchingApply.builder()
                .driveMatching(driveMatchingRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND)))
                .teachers(teachersRepository.findByUserId(driveMatchingApplyRequestDto.getUserId()))
                .build();
        driveMatchingApplyRepository.save(apply);
    }

    @Override
    public void deleteDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto) {
        driveMatchingApplyRepository.deleteById(id);
    }
}

package com.sparta.bochodrive.domain.drivematchingapply.service;

import com.sparta.bochodrive.domain.drivematching.repository.DriveMatchingRepository;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyRequestDto;
import com.sparta.bochodrive.domain.drivematchingapply.dto.DriveMatchingApplyResponseDto;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.repository.DriveMatchingApplyRepository;
import com.sparta.bochodrive.domain.teacher.entity.Teachers;
import com.sparta.bochodrive.domain.teacher.repository.TeachersRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriveMatchingApplyServiceImpl implements DriveMatchingApplyService{

    private final DriveMatchingApplyRepository driveMatchingApplyRepository;
    private final DriveMatchingRepository driveMatchingRepository;
    private final TeachersRepository teachersRepository;

    @Override
    public List<DriveMatchingApplyResponseDto> findDriveMatchingApplys(Long driveMatchingId) {
        List<DriveMatchingApply> list = driveMatchingApplyRepository.findDriveMatchingApplyByDriveMatchingId(driveMatchingId);

        List<DriveMatchingApplyResponseDto> results = list.stream().map(DriveMatchingApplyResponseDto::new).toList();



        return results;

    }

    @Override
    public void addDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto) {
        Teachers teachers = teachersRepository.findByUserId(driveMatchingApplyRequestDto.getUserId());
        if(teachers == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        User user = User.builder().id(driveMatchingApplyRequestDto.getUserId()).build();

        DriveMatchingApply apply = DriveMatchingApply.builder()
                .driveMatching(driveMatchingRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND)))
                .user(user)
                .build();
        driveMatchingApplyRepository.save(apply);
    }

    @Override
    public void deleteDriveMatchingApply(Long id, DriveMatchingApplyRequestDto driveMatchingApplyRequestDto) {
        driveMatchingApplyRepository.deleteById(id);
    }

    @Override
    public boolean validPermission(Long id, User user) {
        DriveMatchingApply driveMatching = getDriveMatchingByMatchingId(id);
        return driveMatching.getUser().getId().equals(user.getId());
    }

    @Override
    public DriveMatchingApply getDriveMatchingByMatchingId(Long id) {
        return driveMatchingApplyRepository.findByDriveMatchingId(id).orElseThrow(() -> new NotFoundException(ErrorCode.CHATROOM_NOT_FOUND));
    }

    public DriveMatchingApply getDriveMatchingApplyById(Long id) {
        return driveMatchingApplyRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.CHATROOM_NOT_FOUND));
    }

}

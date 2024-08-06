package com.sparta.bochodrive.domain.teacher.service;

import com.sparta.bochodrive.domain.teacher.entity.Teachers;
import com.sparta.bochodrive.domain.teacher.repository.TeachersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeachersRepository teachersRepository;

    @Transactional
    public void registTeacher(long userId) {
        Teachers teachers = Teachers.builder()
                .userId(userId)
                .build();
        // 강사 회원가입
        teachersRepository.save(teachers);
    }
}

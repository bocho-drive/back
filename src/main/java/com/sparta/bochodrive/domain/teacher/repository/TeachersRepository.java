package com.sparta.bochodrive.domain.teacher.repository;

import com.sparta.bochodrive.domain.teacher.entity.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachersRepository extends JpaRepository<Teachers, Long> {

    Teachers findByUserId(Long id);
}

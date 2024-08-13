package com.sparta.bochodrive.domain.teacher.repository;

import com.sparta.bochodrive.domain.teacher.entity.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeachersRepository extends JpaRepository<Teachers, Long> {

    Optional<Teachers> findByUserId(Long id);
}

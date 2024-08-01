package com.sparta.bochodrive.domain.challenge.repository;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Page<Challenge> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

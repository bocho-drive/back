package com.sparta.bochodrive.domain.challenge.repository;

import com.sparta.bochodrive.domain.challenge.entity.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}

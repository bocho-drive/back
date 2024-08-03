package com.sparta.bochodrive.domain.challengevarify.repository;


import com.sparta.bochodrive.domain.challengevarify.entity.ChallengeVarify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeVarifyRepository extends JpaRepository<ChallengeVarify, Long> {
    Page<ChallengeVarify> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<ChallengeVarify> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT cv FROM ChallengeVarify cv WHERE cv.community.deleteYN = false ORDER BY cv.createdAt DESC")
    Page<ChallengeVarify> findAllByCommunityDeleteYnFalseOrderByCreatedDateDesc(Pageable pageable);
}

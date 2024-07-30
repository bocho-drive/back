package com.sparta.bochodrive.domain.vote.repository;


import com.sparta.bochodrive.domain.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUserIdAndCommunityId(Long userId, Long communityId);
}

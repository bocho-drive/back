package com.sparta.bochodrive.domain.like.repository;

import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.like.entity.Like;
import com.sparta.bochodrive.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {



    Optional<Like> findByUserAndCommunity(User user, Community community);

    int countByCommunityId(Long id);
}

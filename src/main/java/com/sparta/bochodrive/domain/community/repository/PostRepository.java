package com.sparta.bochodrive.domain.community.repository;

import com.sparta.bochodrive.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Community, Long> {
}

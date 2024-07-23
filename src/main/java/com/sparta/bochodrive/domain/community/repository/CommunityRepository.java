package com.sparta.bochodrive.domain.community.repository;

import com.sparta.bochodrive.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findAllByCategory(String category);
}

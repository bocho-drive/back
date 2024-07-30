package com.sparta.bochodrive.domain.community.repository;

import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findAllByOrderByCreatedAtDesc();
    List<Community> findAllByCategory(CategoryEnum category);
    // deleteYN이 false인 것만 조회하는 메서드 추가
    List<Community> findAllByDeleteYNFalseOrderByCreatedAtDesc();

    List<Community> findAllByCategoryAndDeleteYNFalse(CategoryEnum category);
}

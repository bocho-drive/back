package com.sparta.bochodrive.domain.community.repository;

import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    // deleteYN이 false인 것만 조회하는 메서드
    Page<Community> findAllByDeleteYNFalseOrderByCreatedAtDesc(Pageable pageable);

    // 특정 카테고리와 deleteYN이 false인 것만 조회하는 메서드
    Page<Community> findAllByCategoryAndDeleteYNFalse(CategoryEnum category, Pageable pageable);

}

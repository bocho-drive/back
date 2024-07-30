package com.sparta.bochodrive.domain.community.repository;

import com.sparta.bochodrive.domain.community.entity.CategoryEnum;
import com.sparta.bochodrive.domain.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    // deleteYN이 false인 것만 조회하는 메서드 추가
    Page<Community> findAllByDeleteYNFalseOrderByCreatedAtDesc(Pageable pageable);

    Page<Community> findAllByCategoryAndDeleteYNFalse(CategoryEnum category, Pageable pageable);

    Optional<Community> findByIdAndDeleteYNFalse(Long communityId);

    Page<Community> findfindAllByDeleteYNFalseOrderByCreatedAtDesc(Pageable pageable);
}

package com.sparta.bochodrive.domain.chat.repository;


import com.sparta.bochodrive.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c " +
            "FROM Chat c " +
            "JOIN FETCH c.user u " +
            "WHERE c.driveMatchingApply.id = :driveMatchingApplyId " +
            "ORDER BY c.createdAt DESC")
    List<Chat> findByDriveMatchingApplyOrderByCreatedAt(@Param("driveMatchingApplyId") Long driveMatchingApplyId);
}

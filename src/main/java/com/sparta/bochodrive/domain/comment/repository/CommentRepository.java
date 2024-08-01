package com.sparta.bochodrive.domain.comment.repository;

import com.sparta.bochodrive.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommunityIdAndDeleteYNFalse(Long communitiesId);

    Optional<Comment> findByIdAndDeleteYNFalse(Long commentId);
}

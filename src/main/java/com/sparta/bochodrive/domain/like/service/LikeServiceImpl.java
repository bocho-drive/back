package com.sparta.bochodrive.domain.like.service;


import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.like.dto.LikeRequestDto;
import com.sparta.bochodrive.domain.like.entity.Like;
import com.sparta.bochodrive.domain.like.repository.LikeRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.DuplicateVoteException;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.function.CommonFuntion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final CommunityRepository communityRepository;
    private final CommonFuntion commonFuntion;

    @Override
    public void addLike(LikeRequestDto likeRequestDto, User user) {

        commonFuntion.existsById(user.getId());

        Community community=findCommunityById(likeRequestDto.getCommunityId());

        if(likeRepository.findByUserAndCommunity(user,community).isPresent()){
            throw new DuplicateVoteException(ErrorCode.LIKE_NOT_DUPLICATE);
        }

        Like like = Like.builder()
                .user(user)
                .community(community)
                .build();

        likeRepository.save(like);
        community.setLikeCount(community.getLikeCount()+1);
        communityRepository.save(community);
    }

    @Override
    public void deleteLike(LikeRequestDto likeRequestDto, User user) {

        commonFuntion.existsById(user.getId());

        // 커뮤니티 ID를 LikeRequestDto에서 가져옴
        Long communityId = likeRequestDto.getCommunityId();
        Community community = findCommunityById(communityId);

        // 사용자가 해당 커뮤니티에 누른 좋아요를 찾음
        Optional<Like> likeOptional = likeRepository.findByUserAndCommunity(user, community);

        // 좋아요가 존재하면 삭제
        if (likeOptional.isPresent()) {
            likeRepository.delete(likeOptional.get());
            community.setLikeCount(community.getLikeCount()-1);
            communityRepository.save(community);
        } else {
            throw new NoSuchElementException("좋아요가 존재하지 않습니다.");
        }
    }

    @Override
    public Boolean isLike(Long communityId, User user) {
        // 사용자가 해당 커뮤니티에 누른 좋아요를 찾음
        Optional<Like> likeOptional = likeRepository.findByUserAndCommunity(user, findCommunityById(communityId));

        // 좋아요가 존재하면 true, 존재하지 않으면 false 반환
        return likeOptional.isPresent();
    }

    //community 찾는 메소드
    private Community findCommunityById(Long id) {

        return communityRepository.findById(id)
                .orElseThrow(() -> {
                   return new NotFoundException(ErrorCode.POST_NOT_FOUND);
                });
    }
}

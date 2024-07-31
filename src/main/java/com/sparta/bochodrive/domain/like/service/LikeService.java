package com.sparta.bochodrive.domain.like.service;

import com.sparta.bochodrive.domain.like.dto.LikeRequestDto;
import com.sparta.bochodrive.domain.user.entity.User;

public interface LikeService {
    void addLike(LikeRequestDto likeRequestDto, User user);
    void deleteLike(LikeRequestDto likeRequestDto,User user);


}

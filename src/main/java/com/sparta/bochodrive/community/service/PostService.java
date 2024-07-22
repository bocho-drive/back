package com.sparta.bochodrive.community.service;

import com.sparta.bochodrive.community.dto.PostRequestDto;
import com.sparta.bochodrive.community.dto.PostResponseDto;
import com.sparta.bochodrive.community.repository.PostRepository;
import com.sparta.bochodrive.entity.Post;
import com.sparta.bochodrive.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostService {
    PostRepository postRepository;

    public PostResponseDto addPost(PostRequestDto postRequestDto, User user) {
        return null;
    }
}



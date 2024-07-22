package com.sparta.bochodrive.domain.community.service;

import com.sparta.bochodrive.domain.community.dto.PostListResponseDto;
import com.sparta.bochodrive.domain.community.dto.PostRequestDto;
import com.sparta.bochodrive.domain.community.dto.PostResponseDto;
import com.sparta.bochodrive.domain.community.repository.PostRepository;
import com.sparta.bochodrive.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    PostRepository postRepository;

    public PostResponseDto addPost(PostRequestDto postRequestDto, User user) {
        return null;
    }

    public List<PostListResponseDto> getAllPosts(String category) {
        return null;
    }
}



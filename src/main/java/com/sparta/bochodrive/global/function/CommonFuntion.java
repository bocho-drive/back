package com.sparta.bochodrive.global.function;

import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonFuntion {
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    //인가된 user가 userRepository에 없을 때 사용하는 메소드
    public void existsById(Long id) {
        userRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
    public void deleteCommunity(Long id) {
        Community community = communityRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.POST_NOT_FOUND));
        if(community.isDeleteYN()==true){
            throw new ResourceNotFoundException(ErrorCode.COMMUNITY_DELETE);

        }
    }

}

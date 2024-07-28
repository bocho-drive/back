package com.sparta.bochodrive.global.function;

import com.sparta.bochodrive.domain.user.repository.UserRepository;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonFuntion {
    private final UserRepository userRepository;

    //인가된 user가 userRepository에 없을 때 사용하는 메소드
    public void existsById(Long id) {
        userRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

}

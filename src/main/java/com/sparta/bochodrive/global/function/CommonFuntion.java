package com.sparta.bochodrive.global.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.bochodrive.domain.community.entity.Community;
import com.sparta.bochodrive.domain.community.repository.CommunityRepository;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import com.sparta.bochodrive.global.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CommonFuntion {
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;


    public void deleteCommunity(Long id) {
        Community community = communityRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorCode.POST_NOT_FOUND));
        if(community.isDeleteYN()==true){
            throw new ResourceNotFoundException(ErrorCode.COMMUNITY_DELETE);

        }
    }

    // 서블릿 응답에 JSON body를 추가하는 메소드
    public static void addJsonBodyServletResponse(HttpServletResponse res, Object body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        String s = mapper.writeValueAsString(body);
        res.getWriter().write(s);
    }

}

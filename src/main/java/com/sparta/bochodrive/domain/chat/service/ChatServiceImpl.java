package com.sparta.bochodrive.domain.chat.service;

import com.sparta.bochodrive.domain.chat.dto.ChatResponseDto;
import com.sparta.bochodrive.domain.chat.entity.Chat;
import com.sparta.bochodrive.domain.chat.repository.ChatRepository;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.service.DriveMatchingApplyService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.exception.ErrorCode;
import com.sparta.bochodrive.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final DriveMatchingApplyService driveMatchingApplyService;
    private final JwtUtils jwtUtils;
    private final ChatRepository chatRepository;
    

    /**
     * 특정 방에 채팅 메시지를 전송합니다.
     *
     * @param applyId      채팅 방 ID
     * @param message     전송할 메시지
     * @param user 메시지를 전송하는 사용자
     */
    @Override
    @Transactional
    public ChatResponseDto sendMessage(Long applyId, String message, User user) {

        // 드라이브 매칭 신청 엔티티를 조회합니다.
        DriveMatchingApply driveMatchingApply = driveMatchingApplyService.getDriveMatchingApplyById(applyId);

        // 채팅 메시지를 생성하고 저장합니다.
        Chat chat = Chat.builder()
                .message(message)
                .user(user)
                .driveMatchingApply(driveMatchingApply)
                .build();
        chatRepository.save(chat);

        log.info("방 {}에 메시지를 전송했습니다: {}", applyId, message);
        return new ChatResponseDto(chat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> getChattingList(Long matchingApplyId, CustomUserDetails userDetails) {
        //User user = userDetails.getUser();
//        User user = userRepository.findById(11L).get();
        //driveMatchingApplyService.validPermission(matchingApplyId, user);
        return chatRepository.findByDriveMatchingApplyOrderByCreatedAt(matchingApplyId);
    }

    @Override
    public String getAccessKey(Long matchingApplyId, Long userId) {
        // 매칭신청내역 검증
        DriveMatchingApply driveMatchingApply = driveMatchingApplyService.getDriveMatchingApplyById(matchingApplyId);

        boolean isAble = false;

        // 해당 매칭의 사용자 검증
        if(!driveMatchingApply.getDriveMatching().getUser().getId().equals(userId)) isAble = true;
        // 해당 매칭신청의 사용자 검증
        if(!driveMatchingApply.getUser().getId().equals(userId)) isAble = true;

        if(!isAble) throw new NotFoundException(ErrorCode.CHAT_NOT_AUTH);

        // 접근키 생성
        return jwtUtils.createWebSocketToken(driveMatchingApply.getId(), userId);
    }
}

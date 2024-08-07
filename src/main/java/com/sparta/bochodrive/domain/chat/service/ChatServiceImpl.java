package com.sparta.bochodrive.domain.chat.service;

import com.sparta.bochodrive.domain.chat.entity.Chat;
import com.sparta.bochodrive.domain.chat.repository.ChatRepository;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.service.DriveMatchingApplyService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.global.function.CommonFuntion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final DriveMatchingApplyService driveMatchingApplyService;
    private final CommonFuntion commonFuntion;
    private final ChatRepository chatRepository;
    //private final UserRepository userRepository;

    /**
     * 특정 방에 채팅 메시지를 전송합니다.
     *
     * @param roomId      채팅 방 ID
     * @param message     전송할 메시지
     * @param userDetails 메시지를 전송하는 사용자의 세부 정보
     */
    @Override
    @Transactional
    public void sendMessage(Long roomId, String message, CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        // 방에 대한 사용자의 권한을 확인합니다. (필요 없음)
        driveMatchingApplyService.validPermission(roomId, user);

        // 사용자가 존재하는지 확인합니다.
        commonFuntion.existsById(user.getId());

        // 드라이브 매칭 신청 엔티티를 조회합니다.
        DriveMatchingApply driveMatchingApply = driveMatchingApplyService.getDriveMatching(roomId);

        // 채팅 메시지를 생성하고 저장합니다.
        Chat chat = Chat.builder()
                .message(message)
                .user(user)
                .driveMatchingApply(driveMatchingApply)
                .build();
        chatRepository.save(chat);

        log.info("방 {}에 메시지를 전송했습니다: {}", roomId, message);
    }

    /**
     * 채팅 방 ID로 채팅 방을 찾습니다.
     *
     * @param roomId 채팅 방 ID
     * @return 주어진 방 ID에 해당하는 DriveMatchingApply 엔티티
     * @throws IllegalArgumentException 방을 찾을 수 없는 경우
     */
    @Override
    @Transactional(readOnly = true)
    public DriveMatchingApply findRoomById(Long roomId) {
        return driveMatchingApplyService.getDriveMatching(roomId);
    }
}

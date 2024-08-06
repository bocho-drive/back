package com.sparta.bochodrive.domain.chat.service;

import com.sparta.bochodrive.domain.chat.entity.Chat;
import com.sparta.bochodrive.domain.chat.repository.ChatRepository;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.drivematchingapply.service.DriveMatchingApplyService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.entity.User;
import com.sparta.bochodrive.domain.user.repository.UserRepository;
import com.sparta.bochodrive.global.function.CommonFuntion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final DriveMatchingApplyService driveMatchingApplyService;
    private final CommonFuntion commonFuntion;
    private final ChatRepository chatRepository;
    //private final UserRepository userRepository;


    @Override
    public void sendMessage(Long roomId, String message, CustomUserDetails userDetails) {
        //테스트용
        //User user = userRepository.findById(2L).get();
        User user = userDetails.getUser();
        driveMatchingApplyService.validPermission(roomId, user);
        commonFuntion.existsById(user.getId());
        DriveMatchingApply driveMatchingApply = driveMatchingApplyService.getDriveMatching(roomId);
        Chat chat = Chat.builder()
                .message(message)
                .user(user)
                .driveMatchingApply(driveMatchingApply)
                .build();
        chatRepository.save(chat);

    }

    @Override
    public DriveMatchingApply findRoomById(Long roomId) {
        return driveMatchingApplyService.getDriveMatching(roomId);
    }
}

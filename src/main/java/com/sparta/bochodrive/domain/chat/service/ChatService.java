package com.sparta.bochodrive.domain.chat.service;

import com.sparta.bochodrive.domain.chat.entity.Chat;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;

import java.util.List;

public interface ChatService {

    void sendMessage(Long roomId, String message, CustomUserDetails userDetails);


    /** 이전 채팅 목록 조회 */
    List<Chat> getChattingList(Long matchingApplyId, CustomUserDetails userDetails);

    /** 채팅메시지에 담길 [접근키] 발급 */
    String getAccessKey(Long matchingApplyId, Long userId);
}

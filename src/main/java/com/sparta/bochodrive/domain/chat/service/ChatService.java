package com.sparta.bochodrive.domain.chat.service;

import com.sparta.bochodrive.domain.chat.dto.ChatResponseDto;
import com.sparta.bochodrive.domain.chat.entity.Chat;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.user.entity.User;

import java.util.List;

public interface ChatService {

    /** 채팅 메시지 전송 */
    ChatResponseDto sendMessage(Long roomId, String message, User user);

    /** 이전 채팅 목록 조회 */
    List<Chat> getChattingList(Long matchingApplyId, CustomUserDetails userDetails);

    /** 채팅메시지에 담길 [접근키] 발급 */
    String getAccessKey(Long matchingApplyId, Long userId);
}

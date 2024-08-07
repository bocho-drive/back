package com.sparta.bochodrive.domain.chat.service;

import com.sparta.bochodrive.domain.chat.entity.Chat;
import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;

import java.util.List;

public interface ChatService {

    void sendMessage(Long roomId, String message, CustomUserDetails userDetails);

    DriveMatchingApply findRoomById(Long roomId);

    List<Chat> getChattingList(Long roomId, CustomUserDetails userDetails);
}

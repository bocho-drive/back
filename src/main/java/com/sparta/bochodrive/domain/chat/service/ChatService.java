package com.sparta.bochodrive.domain.chat.service;

import com.sparta.bochodrive.domain.drivematchingapply.entity.DriveMatchingApply;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;

public interface ChatService {

    void sendMessage(Long roomId, String message, CustomUserDetails userDetails);

    DriveMatchingApply findRoomById(Long roomId);
}

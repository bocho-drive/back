package com.sparta.bochodrive.domain.chat.controller;

import com.sparta.bochodrive.domain.chat.dto.ChatResponseDto;
import com.sparta.bochodrive.domain.chat.service.ChatService;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.global.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/{matchingApplyId}")
    public ApiResponse<List<ChatResponseDto>> getMyChatRoom(@PathVariable("matchingApplyId") Long matchingApplyId,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ChatResponseDto> list = chatService.getChattingList(matchingApplyId, userDetails)
                                             .stream().map(ChatResponseDto::new).toList();

        return ApiResponse.ok(HttpStatus.OK.value(), "이전 채팅 목록 조회", list);
    }

    // 채팅방에 들어갈 수 있는 url 생성해주는 API
    @GetMapping("/approval/{matchingApplyId}")
    public ApiResponse<String> getAccessKey(
            @PathVariable("matchingApplyId") Long matchingApplyId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(HttpStatus.OK.value(), "채팅접속키 발급", chatService.getAccessKey(matchingApplyId, userDetails.getUserId()));
    }

}

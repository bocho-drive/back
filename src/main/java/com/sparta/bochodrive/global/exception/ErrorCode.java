package com.sparta.bochodrive.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {


    USER_NOT_FOUND(404,"존재하지 않는 사용자입니다."),
    POST_NOT_FOUND(404,"존재하지 않는 게시글입니다."),
    COMMENT_NOT_FOUND(404,"존재하지 않는 댓글입니다."),
    ADD_FAILED(403,"등록 실패하였습니다."),
    GETLIST_FAILED(403, "조회 실패하였습니다."),
    UPDATE_FAILED(403,"수정에 실패하였습니다."),
    DELETE_FAILED(403,"삭제에 실패하였습니다."),
    VOTE_FAILED(403,"투표 실패");

    private final int status;
    private final String message;

    private ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

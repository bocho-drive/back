package com.sparta.bochodrive.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {


    USER_NOT_FOUND(404,"존재하지 않는 사용자입니다."),
    POST_NOT_FOUND(404,"존재하지 않는 게시글입니다."),
    COMMENT_NOT_FOUND(404,"존재하지 않는 댓글입니다."),
    CHALLENGE_NOT_FOUND(404,"존재하지 않는 챌린지입니다."),
    ADD_FAILED(403,"등록 실패하였습니다."),
    GETLIST_FAILED(403, "조회 실패하였습니다."),
    UPDATE_FAILED(403,"수정에 실패하였습니다."),
    DELETE_FAILED(403,"삭제에 실패하였습니다."),
    VOTE_FAILED(403,"투표 실패"),
    VOTE_NOT_FOUND(404, "존재하지 않은 투표입니다."),
    VOTE_NOT_DUPLICATE(400,"중복 투표는 불가능합니다."),
    LIKE_NOT_DUPLICATE(400,"중복 좋아요는 불가능합니다"),
    LIKE_FAILED(403,"좋아요에 실패하였습니다."),
    FILE_NOT_FOUND(404,"이미지 파일이 존재하지 않습니다."),
    COMMUNITY_DELETE(404,"삭제된 게시글입니다."),
    INVAILD_JWT(401,"Invalid JWT signature, 유효하지 않는 JWT 서명 입니다."),
    EXPIRED_ACCESSTOKEN(401,"accessToken이 재발급되었습니다."),
    EXPIRED_REFRESHTOKEN(401,"쿠키가 만료되었습니다. 다시 로그인하세요."),
    UNSUPPORTED_JWT(401,"Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다."),
    EMPTY_JWT(401,"JWT claims is empty, 잘못된 JWT 토큰 입니다."),
    CHATROOM_NOT_FOUND(404, "존재하지 않는 채팅방입니다."),
    CHAT_NOT_AUTH(404, "사용자의 채팅방을 찾을 수 없습니다."),
    DRIVE_MATCHING_APPLY_ALREADY_EXIST(400, "이미 신청한 드라이브 매칭입니다.");



    private final int status;
    private final String message;

    private ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

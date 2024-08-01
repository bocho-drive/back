package com.sparta.bochodrive.global.exception;

public class DuplicateVoteException extends RuntimeException {
    //엔티티가 목록에 존재하지 않을 때의 에러처리

    private final ErrorCode errorCode;
    public DuplicateVoteException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;

    }
}

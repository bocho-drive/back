package com.sparta.bochodrive.global.exception;

public class DuplicateVoteException extends RuntimeException {

    private final ErrorCode errorCode;
    public DuplicateVoteException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;

    }
}

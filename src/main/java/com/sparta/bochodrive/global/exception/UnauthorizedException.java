package com.sparta.bochodrive.global.exception;


public class UnauthorizedException extends RuntimeException {

    private final ErrorCode errorCode;
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

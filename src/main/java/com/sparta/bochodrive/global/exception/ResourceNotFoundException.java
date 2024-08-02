package com.sparta.bochodrive.global.exception;


public class ResourceNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}


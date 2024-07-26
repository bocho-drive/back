package com.sparta.bochodrive.global.exception;

import jakarta.persistence.EntityNotFoundException;

public class NotFoundException extends EntityNotFoundException {

    //엔티티가 목록에 존재하지 않을 때의 에러처리
    private final ErrorCode errorCode;
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;

    }
}

package com.sparta.bochodrive.global.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

public class NotFoundException extends EntityNotFoundException {
    //404
    @Getter
    private final ErrorCode errorCode;
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;

    }
}

package com.sparta.bochodrive.global.exception;

import org.springframework.http.HttpStatus;


public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"등록 실패하였습니다."),







}

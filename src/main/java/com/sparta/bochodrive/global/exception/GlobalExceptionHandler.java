package com.sparta.bochodrive.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception ex) {
        RestApiException restApiException=new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(restApiException, HttpStatus.INTERNAL_SERVER_ERROR);}


    //400 -> 회원가입할 때 데이터 관련 예외
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiException> IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    //404 -> 개발자 예상 가능
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<RestApiException> NotFoundExceptionHandler(NotFoundException ex) {
        RestApiException restApiException = new RestApiException(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.NOT_FOUND
        );
    }

    //403
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<RestApiException> UnauthorizedExceptionHandler(UnauthorizedException ex) {
        RestApiException restApiException = new RestApiException(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.FORBIDDEN
        );
    }
}

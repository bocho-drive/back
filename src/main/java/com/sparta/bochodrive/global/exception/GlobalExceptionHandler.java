package com.sparta.bochodrive.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    //커스텀하지 않은 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception ex) {
        RestApiException restApiException=new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(restApiException, HttpStatus.INTERNAL_SERVER_ERROR);}


    //400
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

    //404
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<RestApiException> NotFoundExceptionHandler(NotFoundException ex) {
        RestApiException restApiException = new RestApiException(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.NOT_FOUND
        );
    }
}

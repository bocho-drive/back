package com.sparta.bochodrive.global.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {

    private int statusCode;
    private String message;
    private T data;


    public Message(HttpStatus httpStatus, String msg, T data) {
        this.statusCode = httpStatus.value();
        this.message = msg;
        this.data = data;
    }
}
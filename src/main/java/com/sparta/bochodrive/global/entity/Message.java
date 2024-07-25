package com.sparta.bochodrive.global.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Message<T> {
    int statusCode;
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) //null일 때는 json에 포함을 안함
    T data=null;

    public Message(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}

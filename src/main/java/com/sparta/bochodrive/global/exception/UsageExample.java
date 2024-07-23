//package com.sparta.bochodrive.global.exception;
//
//import org.springframework.data.domain.Example;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//
//public class UsageExample {
//
//
//    //컨트롤
//    @PostMapping
//    public ResponseEntity<UsageExample> updateUsageExample(UsageExampleDto usageExampleDto) {
//
//        return new ResponseEntity<>(returnRequestDto, HttpStatus.OK);
//    }
//
//
//
//    //서비스
//
//    public ResponseDTo updateUsageExample(UsageExampleDto usageExampleDto) {
//        Example example = findSomething(someId);
//        example.update(usageExampleDto);
//
//        return new ResponseDTo(example);
//    }
//
//    private Example findSomething(Long someId) {
//        //레포지토리 쿼리메소드 findById로 받아 온 후
//        if (/** 아이디가 존재 하지 않는 경우**/)
//            throw new IllegalArgumentException("알릴 메시지");
//    }
//
//}

package com.sparta.bochodrive.domain.challenge.controller;

import com.sparta.bochodrive.domain.challenge.service.ChallengeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeServiceImpl challengeService;


}

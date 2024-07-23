package com.sparta.bochodrive.domain.challenge.service;

import com.sparta.bochodrive.domain.challenge.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengService {
    private final ChallengeRepository challengeRepository;
}

package com.sparta.bochodrive.domain.community.entity;

public enum CategoryEnum {
    GENERAL("일반게시판"),
    VOTE("투표 게시판"),
    TIP("TIP게시판"),
    CHALLENGE_VERIFY("챌린지인증게시판");

    private final String description;

    CategoryEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
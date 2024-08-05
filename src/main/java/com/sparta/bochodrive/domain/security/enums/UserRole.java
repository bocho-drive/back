package com.sparta.bochodrive.domain.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum UserRole {

    USER("USER"),
    ADMIN("ADMIN"),
    TEACHER("TEACHER");

    private final String role;


    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
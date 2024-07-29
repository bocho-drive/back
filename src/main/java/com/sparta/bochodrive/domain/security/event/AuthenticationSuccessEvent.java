package com.sparta.bochodrive.domain.security.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

@Getter
public class AuthenticationSuccessEvent extends ApplicationEvent {
    private final Authentication authentication;

    public AuthenticationSuccessEvent(Object source, Authentication authentication) {
        super(source);
        this.authentication = authentication;
    }
}
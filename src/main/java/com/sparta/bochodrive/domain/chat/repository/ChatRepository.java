package com.sparta.bochodrive.domain.chat.repository;


import com.sparta.bochodrive.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}

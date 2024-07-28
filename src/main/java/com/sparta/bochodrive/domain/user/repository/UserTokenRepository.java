package com.sparta.bochodrive.domain.user.repository;

import com.sparta.bochodrive.domain.user.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

}

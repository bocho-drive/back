package com.sparta.bochodrive.domain.user.entity;

import com.sparta.bochodrive.domain.security.enums.UserRole;
import com.sparta.bochodrive.domain.user.model.UserModel;
import com.sparta.bochodrive.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "DELETE_YN", nullable = false)
    private boolean deleteYN;

    @Column(name = "USER_ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public static User of(UserModel.UserRegistDto userRegistDto, BCryptPasswordEncoder encoder) {
        return User.builder()
                .email(userRegistDto.getEmail())
                .password(encoder.encode(userRegistDto.getPassword()))
                .nickname(userRegistDto.getNickname())
                .deleteYN(false)
                .userRole(UserRole.USER)
                .build();
    }

    public UserModel.UserResponseDto toDto() {
        return UserModel.UserResponseDto.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .build();
    }
}
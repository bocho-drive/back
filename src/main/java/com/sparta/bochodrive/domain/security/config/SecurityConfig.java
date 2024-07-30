package com.sparta.bochodrive.domain.security.config;

import com.sparta.bochodrive.domain.security.filter.JwtFilter;
import com.sparta.bochodrive.domain.security.filter.LoginFilter;
import com.sparta.bochodrive.domain.security.model.CustomUserDetails;
import com.sparta.bochodrive.domain.security.service.CustomerUserDetailsService;
import com.sparta.bochodrive.domain.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final CustomerUserDetailsService customUserDetails;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // CSRF disable
                .formLogin(AbstractHttpConfigurer::disable) // Form Login disable
                .httpBasic(AbstractHttpConfigurer::disable);    // Http basic 인증 방식 disable

        // 경로별 인가 여부
        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/v1/user/signup").permitAll()
                        .requestMatchers("/api/v1/user/signin").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/auth/login").hasRole("ADMIN")
                        .anyRequest().permitAll());

        // 필터 추가
        httpSecurity
                .addFilterBefore(new JwtFilter(jwtUtils,customUserDetails), LoginFilter.class);

        httpSecurity
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtils), UsernamePasswordAuthenticationFilter.class);
        // 세션 설정
        httpSecurity
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
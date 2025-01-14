package com.project.dust.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoding() {
        String password = "java1234";
        String encodedPassword = passwordEncoder.encode(password);

        log.info("기존 비밀번호: {}", password);
        log.info("암호화된 비밀번호: {}", encodedPassword);
        log.info("암호화된 비밀번호 길이: {}", encodedPassword.length());

        Assertions.assertThat(passwordEncoder.matches(password, encodedPassword)).isTrue();
    }

}
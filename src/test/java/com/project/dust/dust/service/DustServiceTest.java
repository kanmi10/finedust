package com.project.dust.dust.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class DustServiceTest {

    @Autowired
    private DustService dustService;

    @Test
    @DisplayName("대기오염 데이터 초기화")
    void dustInit() {
        dustService.init("전국");
    }

    @Test
    @DisplayName("<foreach>를 이용한 다중 쿼리 update")
    void foreachUpdate() {
        dustService.foreachUpdate("전국");
    }

    @Test
    @DisplayName("반복문을 이용한 다건 update")
    void loopUpdate() {
        dustService.loopUpdate("전국");
    }

    @Test
    @DisplayName("SqlSession Execute.BATCH를 이용한 배치처리")
    void BulkInsertUsingSqlSession() {
        dustService.batchUpdate("전국");
    }

}
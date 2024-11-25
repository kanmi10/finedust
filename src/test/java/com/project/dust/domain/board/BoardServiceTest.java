package com.project.dust.domain.board;

import com.project.dust.board.Type;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
class BoardServiceTest {

    @Test
    @DisplayName("ENUM 테스트")
    void searchBoardsEnumTest() {
        String typeName = Arrays.stream(Type.values())
                .filter(type -> type.getTypeNumber() == 1)
                .map(Type::getTypeName)
                .findFirst()
                .orElse(null);

        log.info("typeName={}", typeName);

    }

}